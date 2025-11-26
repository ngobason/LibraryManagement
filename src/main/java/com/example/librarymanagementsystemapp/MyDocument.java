package com.example.librarymanagementsystemapp;

import java.sql.Date;

public class MyDocument {
    private int transactionId;
    private int documentId;
    private String documentName;
    private String documentAuthor;
    private String documentGenre;
    private Date borrowDate;
    private Date dueDate;

    public MyDocument(int transactionId,int documentId, String documentName, String documentAuthor, String documentGenre, Date borrowDate, Date dueDate) {
        this.transactionId = transactionId;
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentAuthor = documentAuthor;
        this.documentGenre = documentGenre;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getDocumentAuthor() {
        return documentAuthor;
    }

    public void setDocumentAuthor(String documentAuthor) {
        this.documentAuthor = documentAuthor;
    }

    public String getDocumentGenre() {
        return documentGenre;
    }

    public void setDocumentGenre(String documentGenre) {
        this.documentGenre = documentGenre;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }
}

