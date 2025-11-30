package com.example.librarymanagementsystemapp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CardControllerTest extends JavaFXTestBase {

    private CardController controller;
    private Document mockDocument;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CardController();

        setPrivateField(controller, "Author", new Label());
        setPrivateField(controller, "Genre", new Label());
        setPrivateField(controller, "title", new Label());
        setPrivateField(controller, "documentImage", new ImageView());
        setPrivateField(controller, "borrowButton", new Button());
        setPrivateField(controller, "box", new HBox());

        mockDocument = Mockito.mock(Document.class);

        Mockito.when(mockDocument.getTitle()).thenReturn("Software Engineering");
        Mockito.when(mockDocument.getAuthor()).thenReturn("Nguyen Van A");
        Mockito.when(mockDocument.getGenre()).thenReturn("Technology");
        Mockito.when(mockDocument.getImage()).thenReturn("https://example.com/img.png");
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testSetData_assignsCorrectTexts() {
        controller.setData(mockDocument);

        Label title = (Label) getPrivateValue(controller, "title");
        Label author = (Label) getPrivateValue(controller, "Author");
        Label genre = (Label) getPrivateValue(controller, "Genre");

        assertEquals("Software Engineering", title.getText());
        assertEquals("Nguyen Van A", author.getText());
        assertEquals("Technology", genre.getText());
    }

    @Test
    void testSetData_setsImageCorrectly() {
        controller.setData(mockDocument);

        ImageView img = (ImageView) getPrivateValue(controller, "documentImage");
        assertEquals("https://example.com/img.png", img.getImage().getUrl());
    }

    @Test
    void testSetData_setsRandomBackgroundColor() {
        controller.setData(mockDocument);

        HBox box = (HBox) getPrivateValue(controller, "box");
        assertTrue(box.getStyle().contains("-fx-background-color"));
    }

    @Test
    void testBorrowButton_actionIsSet() {
        controller.setData(mockDocument);

        Button btn = (Button) getPrivateValue(controller, "borrowButton");
        assertNotNull(btn.getOnAction());
    }

    @Test
    void testSetBorrowButton_doesNotThrow() {
        assertDoesNotThrow(() -> controller.setBorrowButton(mockDocument));
    }

    private Object getPrivateValue(Object target, String fieldName) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
