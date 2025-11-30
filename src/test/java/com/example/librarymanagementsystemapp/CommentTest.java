package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment(
                "https://example.com/image.png",
                "Nguyen Van A",
                "This is a sample comment"
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("https://example.com/image.png", comment.getImage());
        assertEquals("Nguyen Van A", comment.getName());
        assertEquals("This is a sample comment", comment.getComment());
    }

    @Test
    void testSetImage() {
        comment.setImage("https://example.com/new_image.png");
        assertEquals("https://example.com/new_image.png", comment.getImage());
    }

    @Test
    void testSetName() {
        comment.setName("Pham Thi B");
        assertEquals("Pham Thi B", comment.getName());
    }

    @Test
    void testSetComment() {
        comment.setComment("Updated comment text");
        assertEquals("Updated comment text", comment.getComment());
    }
}
