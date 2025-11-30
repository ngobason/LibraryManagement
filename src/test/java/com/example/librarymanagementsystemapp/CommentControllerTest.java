package com.example.librarymanagementsystemapp;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CommentControllerTest extends JavaFXTestBase {

    private CommentController controller;
    private Comment sampleComment;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CommentController();

        setPrivateField(controller, "image", new ImageView());
        setPrivateField(controller, "name", new Label());
        setPrivateField(controller, "comment", new Label());

        sampleComment = new Comment(
                "test.png",
                "Nguyen Van A",
                "This is a test comment"
        );
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Object getPrivateField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSetData_assignsCorrectValues() {
        controller.setData(sampleComment);

        ImageView imageView = (ImageView) getPrivateField(controller, "image");
        Label nameLabel = (Label) getPrivateField(controller, "name");
        Label commentLabel = (Label) getPrivateField(controller, "comment");

        assertEquals("file:" + sampleComment.getImage(), imageView.getImage().getUrl());
        assertEquals("Nguyen Van A", nameLabel.getText());
        assertEquals("This is a test comment", commentLabel.getText());
    }

    @Test
    void testSetImage_setsCircleClip() {
        controller.setImage();

        ImageView imageView = (ImageView) getPrivateField(controller, "image");
        assertNotNull(imageView.getClip());
        assertEquals(javafx.scene.shape.Circle.class, imageView.getClip().getClass());
    }
}
