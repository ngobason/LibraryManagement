package com.example.librarymanagementsystemapp;

public class User {
    private int id;
    private String userName;
    private String fullName;
    private String gender;
    private String phone;
    private String gmail;
    public User(int id, String userName, String fullName, String phone, String gender, String gmail) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.gmail = gmail;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public String getGmail() {
        return gmail;
    }
}
