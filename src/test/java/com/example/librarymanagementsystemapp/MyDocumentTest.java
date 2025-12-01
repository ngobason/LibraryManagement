package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

public class MyDocumentTest {

    @Test
    void testConstructorAndGetters() {
        Date borrowDate = Date.valueOf("2025-11-30");
        Date dueDate = Date.valueOf("2025-12-15");

        MyDocument doc = new MyDocument(
                10,
                20,
                "JavaFX Basics",
                "Nguyen Van A",
                "Technology",
                borrowDate,
                dueDate
        );

        assertEquals(10, doc.getTransactionId());
        assertEquals(20, doc.getDocumentId());
        assertEquals("JavaFX Basics", doc.getDocumentName());
        assertEquals("Nguyen Van A", doc.getDocumentAuthor());
        assertEquals("Technology", doc.getDocumentGenre());
        assertEquals(borrowDate, doc.getBorrowDate());
        assertEquals(dueDate, doc.getDueDate());
    }

    @Test
    void testSetDocumentId() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Nguyen Van A", "Fiction",
                Date.valueOf("2025-11-30"), Date.valueOf("2025-12-10"));

        doc.setDocumentId(50);
        assertEquals(50, doc.getDocumentId());
    }

    @Test
    void testSetTransactionId() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Nguyen Van A", "Fiction",
                Date.valueOf("2025-11-30"), Date.valueOf("2025-12-10"));

        doc.setTransactionId(99);
        assertEquals(99, doc.getTransactionId());
    }

    @Test
    void testSetDocumentName() {
        MyDocument doc = new MyDocument(1, 2, "Old", "Nguyen Van A", "Fiction",
                Date.valueOf("2025-11-30"), Date.valueOf("2025-12-10"));

        doc.setDocumentName("New Name");
        assertEquals("New Name", doc.getDocumentName());
    }

    @Test
    void testSetDocumentAuthor() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Old Author", "Fiction",
                Date.valueOf("2025-11-30"), Date.valueOf("2025-12-10"));

        doc.setDocumentAuthor("Pham Thi B");
        assertEquals("Pham Thi B", doc.getDocumentAuthor());
    }

    @Test
    void testSetDocumentGenre() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Nguyen Van A", "Old Genre",
                Date.valueOf("2025-11-30"), Date.valueOf("2025-12-10"));

        doc.setDocumentGenre("Science");
        assertEquals("Science", doc.getDocumentGenre());
    }

    @Test
    void testSetBorrowDate() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Nguyen Van A", "Fiction",
                Date.valueOf("2025-11-01"), Date.valueOf("2025-12-01"));

        Date newBorrow = Date.valueOf("2025-11-25");
        doc.setBorrowDate(newBorrow);
        assertEquals(newBorrow, doc.getBorrowDate());
    }

    @Test
    void testSetDueDate() {
        MyDocument doc = new MyDocument(1, 2, "Book", "Nguyen Van A", "Fiction",
                Date.valueOf("2025-11-01"), Date.valueOf("2025-12-01"));

        Date newDue = Date.valueOf("2025-12-20");
        doc.setDueDate(newDue);
        assertEquals(newDue, doc.getDueDate());
    }
}
