package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class BorrowTransactionTest {

    private BorrowTransaction btBasic;
    private BorrowTransaction btWithReturn;

    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;

    @BeforeEach
    void setUp() {
        borrowDate = Date.valueOf("2025-01-10");
        dueDate = Date.valueOf("2025-01-20");
        returnDate = Date.valueOf("2025-01-18");

        btBasic = new BorrowTransaction(
                1,
                "Java Programming",
                "Nguyen Van A",
                borrowDate,
                dueDate,
                "Borrowed"
        );

        btWithReturn = new BorrowTransaction(
                2,
                "DSA Book",
                "Pham Van B",
                borrowDate,
                dueDate,
                "Returned",
                returnDate
        );
    }

    @Test
    void testConstructor_basicFieldsInitialized() {
        assertEquals(1, btBasic.getTransactionId());
        assertEquals("Java Programming", btBasic.getDocumentName());
        assertEquals("Nguyen Van A", btBasic.getBorrowerName());
        assertEquals(borrowDate, btBasic.getBorrowDate());
        assertEquals(dueDate, btBasic.getDueDate());
        assertEquals("Borrowed", btBasic.getStatus());
        assertNotNull(btBasic.getSelected());
        assertFalse(btBasic.isSelected());
    }

    @Test
    void testConstructor_withReturnDateInitialized() {
        assertEquals(2, btWithReturn.getTransactionId());
        assertEquals("DSA Book", btWithReturn.getDocumentName());
        assertEquals("Pham Van B", btWithReturn.getBorrowerName());
        assertEquals(borrowDate, btWithReturn.getBorrowDate());
        assertEquals(dueDate, btWithReturn.getDueDate());
        assertEquals("Returned", btWithReturn.getStatus());
        assertEquals(returnDate, btWithReturn.getReturnDate());
    }

    @Test
    void testSelectedProperty_defaultIsFalse() {
        assertFalse(btBasic.isSelected());
    }

    @Test
    void testSetSelected_updatesValue() {
        btBasic.setSelected(true);
        assertTrue(btBasic.isSelected());

        btBasic.setSelected(false);
        assertFalse(btBasic.isSelected());
    }

    @Test
    void testSetTransactionId_updatesValue() {
        btBasic.setTransactionId(99);
        assertEquals(99, btBasic.getTransactionId());
    }

    @Test
    void testSetBorrowerName_updatesValue() {
        btBasic.setBorrowerName("New Name");
        assertEquals("New Name", btBasic.getBorrowerName());
    }

    @Test
    void testSetDocumentName_updatesValue() {
        btBasic.setDocumentName("New Book");
        assertEquals("New Book", btBasic.getDocumentName());
    }

    @Test
    void testSetBorrowDate_updatesValue() {
        Date newDate = Date.valueOf("2025-02-01");
        btBasic.setBorrowDate(newDate);
        assertEquals(newDate, btBasic.getBorrowDate());
    }

    @Test
    void testSetDueDate_updatesValue() {
        Date newDate = Date.valueOf("2025-03-01");
        btBasic.setDueDate(newDate);
        assertEquals(newDate, btBasic.getDueDate());
    }

    @Test
    void testSetStatus_updatesValue() {
        btBasic.setStatus("Late");
        assertEquals("Late", btBasic.getStatus());
    }
}
