package com.example.librarymanagementsystemapp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class Document2ControllerTest extends JavaFXTestBase {

    private Document2Controller controller;
    private Document sampleDocument;

    @BeforeEach
    void setUp() throws Exception {
        controller = new Document2Controller();

        setPrivateField(controller, "documentAuthor", new Label());
        setPrivateField(controller, "documentImage", new ImageView());
        setPrivateField(controller, "documentName", new Label());
        setPrivateField(controller, "borrowButton", new Button());

        Document anotherDocument = new Document(
            2,           
            "Learning Java", 
            "Pham Thi B",   
            "Education",    
            5,               
            "java_book.png"    
        );
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Object getPrivateField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    @Test
    void testSetData_assignsValuesCorrectly() throws Exception {
        controller.setData(sampleDocument);

        Label nameLabel = (Label) getPrivateField(controller, "documentName");
        Label authorLabel = (Label) getPrivateField(controller, "documentAuthor");
        ImageView imageView = (ImageView) getPrivateField(controller, "documentImage");

        assertEquals("Test Book", nameLabel.getText());
        assertEquals("Nguyen Van A", authorLabel.getText());
        assertEquals("test.png", imageView.getImage().getUrl());
    }

    @Test
    void testSetAddButton_doesNotThrow() {
        assertDoesNotThrow(() -> controller.setAddButton(sampleDocument));
    }
}
