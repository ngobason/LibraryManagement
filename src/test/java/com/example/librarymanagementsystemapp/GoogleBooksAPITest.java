package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GoogleBooksAPITest {

    @Test
    void testSearchBooksReturnsParsedDocuments() throws Exception {
        // JSON giả lập giống response thật của Google Books API
        String fakeJson = """
        {
          "items": [
            {
              "volumeInfo": {
                "title": "Clean Code",
                "authors": ["Robert C. Martin"],
                "categories": ["Programming"],
                "imageLinks": { "thumbnail": "http://image.com/img1" }
              }
            },
            {
              "volumeInfo": {
                "title": "Design Patterns",
                "authors": ["Erich Gamma", "Richard Helm"],
                "categories": ["Software Engineering"],
                "imageLinks": { "thumbnail": "http://image.com/img2" }
              }
            }
          ]
        }
        """;

        // Mock HttpURLConnection
        HttpURLConnection mockConn = mock(HttpURLConnection.class);
        when(mockConn.getInputStream()).thenReturn(new ByteArrayInputStream(fakeJson.getBytes()));

        // Mock URL để trả về connection giả
        MockedStatic<URL> mockedURL = Mockito.mockStatic(URL.class);
        URL fakeUrl = mock(URL.class);

        mockedURL.when(() -> new URL(anyString())).thenReturn(fakeUrl);
        when(fakeUrl.openConnection()).thenReturn(mockConn);

        // Run method cần test
        List<Document> result = GoogleBooksAPI.searchBooks(List.of("java"));

        // Kiểm tra dữ liệu trả về
        assertEquals(2, result.size());

        Document d1 = result.get(0);
        assertEquals("Clean Code", d1.getTitle());
        assertEquals("Robert C. Martin", d1.getAuthor());
        assertEquals("Programming", d1.getGenre());
        assertEquals("http://image.com/img1", d1.getImage());

        Document d2 = result.get(1);
        assertEquals("Design Patterns", d2.getTitle());
        assertEquals("Erich Gamma, Richard Helm", d2.getAuthor());
        assertEquals("Software Engineering", d2.getGenre());
        assertEquals("http://image.com/img2", d2.getImage());

        mockedURL.close();
    }

    @Test
    void testSearchBooksHandlesEmptyResponse() throws Exception {
        String emptyJson = "{ \"items\": [] }";

        HttpURLConnection mockConn = mock(HttpURLConnection.class);
        when(mockConn.getInputStream()).thenReturn(new ByteArrayInputStream(emptyJson.getBytes()));

        MockedStatic<URL> mockedURL = Mockito.mockStatic(URL.class);
        URL fakeUrl = mock(URL.class);

        mockedURL.when(() -> new URL(anyString())).thenReturn(fakeUrl);
        when(fakeUrl.openConnection()).thenReturn(mockConn);

        List<Document> result = GoogleBooksAPI.searchBooks(List.of("java"));

        assertTrue(result.isEmpty());

        mockedURL.close();
    }

    @Test
    void testSearchBooksHandlesException() throws Exception {
        // Giả lập lỗi khi mở kết nối
        MockedStatic<URL> mockedURL = Mockito.mockStatic(URL.class);
        mockedURL.when(() -> new URL(anyString())).thenThrow(new RuntimeException("Connection failed"));

        List<Document> result = GoogleBooksAPI.searchBooks(List.of("java"));

        // Khi có exception → phương thức trả về list rỗng
        assertTrue(result.isEmpty());

        mockedURL.close();
    }
}
