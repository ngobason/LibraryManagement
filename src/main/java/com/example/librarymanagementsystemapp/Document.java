package com.example.librarymanagementsystemapp;

public class Document {
        private int id;
        private String title;
        private String author;
        private String genre;
        private int quantity;
        private String image;

        public Document() {
        }

        public Document(int id, String title, String author, String genre, int quantity, String image) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.quantity = quantity;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String name) {
            this.title = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
}
