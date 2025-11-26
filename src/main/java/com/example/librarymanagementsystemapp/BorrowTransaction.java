package com.example.librarymanagementsystemapp;

import javafx.beans.property.SimpleBooleanProperty;

import java.sql.Date;

public class BorrowTransaction {
    private int transactionId;
    private String documentName;
    private String borrowerName;
    private Date borrowDate;
    private Date dueDate;
    private String status;
    private Date returnDate;
    private SimpleBooleanProperty selected;

    public BorrowTransaction(int transactionId, String documentName, String borrowerName, Date borrowDate, Date dueDate, String status) {
        this.transactionId = transactionId;
        this.documentName = documentName;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
        this.selected = new SimpleBooleanProperty(false);
    }

    public BorrowTransaction(int transactionId, String documentName, String borrowerName, Date borrowDate, Date dueDate, String status, Date returnDate) {
        this.transactionId = transactionId;
        this.documentName = documentName;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
        this.returnDate = returnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public SimpleBooleanProperty getSelected() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(Boolean selected) {
        this.selected.set(selected);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
