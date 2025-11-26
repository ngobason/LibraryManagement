package com.example.librarymanagementsystemapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GoogleBooksAPI {
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String API_KEY = "GOOGLE_API_KEY";

    public static List<Document> searchBooks(List<String> queries) {
        List<Document> books = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(queries.size()); // Tạo thread pool
        List<Callable<List<Document>>> tasks = new ArrayList<>();

        // Thêm các tác vụ song song
        for (String query : queries) {
            tasks.add(() -> fetchBooks(query));
        }

        try {
            // Thực thi song song
            List<Future<List<Document>>> results = executor.invokeAll(tasks);
            for (Future<List<Document>> result : results) {
                books.addAll(result.get()); // Gộp kết quả
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Đảm bảo thread pool được tắt
        }
        return books;
    }

    private static List<Document> fetchBooks(String query) {
        List<Document> books = new ArrayList<>();
        try {
            String urlString = API_URL + "?q=" + query + "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray items = jsonResponse.optJSONArray("items");
            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                    String title = volumeInfo.optString("title", "Unknown Title");
                    String author = volumeInfo.optJSONArray("authors") != null
                            ? volumeInfo.getJSONArray("authors").join(", ").replace("\"", "")
                            : "Unknown Author";
                    String genre = volumeInfo.optJSONArray("categories") != null
                            ? volumeInfo.getJSONArray("categories").join(", ").replace("\"", "")
                            : "Unknown Genre";
                    String image = volumeInfo.optJSONObject("imageLinks") != null
                            ? volumeInfo.getJSONObject("imageLinks").optString("thumbnail")
                            : null;

                    books.add(new Document(0, title, author, genre, 1, image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
