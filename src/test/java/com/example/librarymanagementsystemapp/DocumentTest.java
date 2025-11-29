package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    @Test
    void testDefaultConstructor() {
        Document doc = new Document();

        assertEquals(0, doc.getId());
        assertNull(doc.getTitle());
        assertNull(doc.getAuthor());
        assertNull(doc.getGenre());
        assertEquals(0, doc.getQuantity());
        assertNull(doc.getImage());
    }

    @Test
    void testParameterizedConstructor() {
        Document doc = new Document(1, "Clean Code", "Robert", "Programming", 5, "image.jpg");

        assertEquals(1, doc.getId());
        assertEquals("Clean Code", doc.getTitle());
        assertEquals("Robert", doc.getAuthor());
        assertEquals("Programming", doc.getGenre());
        assertEquals(5, doc.getQuantity());
        assertEquals("image.jpg", doc.getImage());
    }

    @Test
    void testSetAndGetId() {
        Document doc = new Document();
        doc.setId(10);
        assertEquals(10, doc.getId());
    }

    @Test
    void testSetAndGetTitle() {
        Document doc = new Document();
        doc.setTitle("Harry Potter");
        assertEquals("Harry Potter", doc.getTitle());
    }

    @Test
    void testSetAndGetAuthor() {
        Document doc = new Document();
        doc.setAuthor("J.K. Rowling");
        assertEquals("J.K. Rowling", doc.getAuthor());
    }

    @Test
    void testSetAndGetGenre() {
        Document doc = new Document();
        doc.setGenre("Fantasy");
        assertEquals("Fantasy", doc.getGenre());
    }

    @Test
    void testSetAndGetQuantity() {
        Document doc = new Document();
        doc.setQuantity(3);
        assertEquals(3, doc.getQuantity());
    }

    @Test
    void testSetAndGetImage() {
        Document doc = new Document();
        doc.setImage("cover.png");
        assertEquals("cover.png", doc.getImage());
    }
}
