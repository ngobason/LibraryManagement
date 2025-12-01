package com.example.librarymanagementsystemapp;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentDetailsControllerTest extends JavaFXTestBase {

    private DocumentDetailsController controller;

    private TextField documentId;
    private TextField documentTitle;
    private TextField documentAuthor;
    private TextField documentGenre;
    private TextField documentQuantity;
    private ImageView documentImage;
    private Button addButton;

    private Stage stage;
    private Document document;

    @BeforeEach
    void setUp() throws Exception {
        controller = new DocumentDetailsController();

        documentId = new TextField();
        documentTitle = new TextField();
        documentAuthor = new TextField();
        documentGenre = new TextField();
        documentQuantity = new TextField();
        documentImage = new ImageView();
        addButton = new Button();

        stage = new Stage();

        document = new Document(
                1,
                "Java Programming",
                "Nguyen Van A",
                "Technology",
                10,
                "java_book.png"
        );

        setField("documentId", documentId);
        setField("documentTitle", documentTitle);
        setField("documentAuthor", documentAuthor);
        setField("documentGenre", documentGenre);
        setField("documentQuantity", documentQuantity);
        setField("documentImage", documentImage);
        setField("addButton", addButton);
    }

    private void setField(String name, Object value) throws Exception {
        Field f = DocumentDetailsController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, value);
    }

    @Test
    void testSetDocument() {
        controller.setDocument(document, stage);

        assertEquals("Java Programming", documentTitle.getText());
        assertEquals("Nguyen Van A", documentAuthor.getText());
        assertEquals("Technology", documentGenre.getText());
        assertNotNull(addButton.getOnAction());
    }

    @Test
    void testAddDocument_MissingField() {
        documentId.setText("");
        documentTitle.setText("Learning Java");
        documentAuthor.setText("Pham Thi B");
        documentGenre.setText("Education");
        documentQuantity.setText("5");

        assertDoesNotThrow(() -> controller.addDocumentToDataBase(document, stage));
    }

    @Test
    void testAddDocument_InvalidId() {
        documentId.setText("abc");
        documentTitle.setText("Learning Java");
        documentAuthor.setText("Pham Thi B");
        documentGenre.setText("Education");
        documentQuantity.setText("5");

        assertDoesNotThrow(() -> controller.addDocumentToDataBase(document, stage));
    }

    @Test
    void testAddDocument_InvalidQuantity_NotNumber() {
        documentId.setText("12");
        documentTitle.setText("Learning Java");
        documentAuthor.setText("Pham Thi B");
        documentGenre.setText("Education");
        documentQuantity.setText("abc");

        assertDoesNotThrow(() -> controller.addDocumentToDataBase(document, stage));
    }

    @Test
    void testAddDocument_InvalidQuantity_LessThanZero() {
        documentId.setText("15");
        documentTitle.setText("Learning Java");
        documentAuthor.setText("Pham Thi B");
        documentGenre.setText("Education");
        documentQuantity.setText("-3");

        assertDoesNotThrow(() -> controller.addDocumentToDataBase(document, stage));
    }

    @Test
    void testAddDocument_ValidData() {
        documentId.setText("100");
        documentTitle.setText("Learning Java");
        documentAuthor.setText("Pham Thi B");
        documentGenre.setText("Education");
        documentQuantity.setText("5");

        assertDoesNotThrow(() -> controller.addDocumentToDataBase(document, stage));
    }
}
