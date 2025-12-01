package com.example.librarymanagementsystemapp;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentDetails2ControllerTest extends JavaFXTestBase {

    private DocumentDetails2Controller controller;

    private Label documentAuthor;
    private Label documentGenre;
    private Label documentTitle;
    private ImageView documentImage;
    private TextField borrowDate;
    private DatePicker dueDate;
    private Button borrowButton;
    private Button sendCommentButton;
    private TextField comment;
    private GridPane commentContainer;

    private Stage stage;
    private Document document;

    @BeforeEach
    void setUp() throws Exception {
        controller = new DocumentDetails2Controller();

        documentAuthor = new Label();
        documentGenre = new Label();
        documentTitle = new Label();
        documentImage = new ImageView();
        borrowDate = new TextField();
        dueDate = new DatePicker();
        borrowButton = new Button();
        sendCommentButton = new Button();
        comment = new TextField();
        commentContainer = new GridPane();

        stage = new Stage();

        document = new Document(
                2,
                "Learning Java",
                "Pham Thi B",
                "Education",
                5,
                "java_book.png"
        );

        setField("documentAuthor", documentAuthor);
        setField("documentGenre", documentGenre);
        setField("documentTitle", documentTitle);
        setField("documentImage", documentImage);
        setField("borrowDate", borrowDate);
        setField("dueDate", dueDate);
        setField("borrowButton", borrowButton);
        setField("sendCommentButton", sendCommentButton);
        setField("comment", comment);
        setField("commentContainer", commentContainer);
    }

    private void setField(String name, Object value) throws Exception {
        Field f = DocumentDetails2Controller.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, value);
    }

    @Test
    void testSetDocument() {
        controller.setDocument(document, stage);

        assertEquals("Learning Java", documentTitle.getText());
        assertTrue(documentAuthor.getText().contains("Pham Thi B"));
        assertTrue(documentGenre.getText().contains("Education"));
        assertFalse(borrowDate.getText().isEmpty());
        assertNotNull(borrowButton.getOnAction());
        assertNotNull(sendCommentButton.getOnAction());
    }

    @Test
    void testSetSendCommentButton() {
        comment.setText("Useful book");

        assertDoesNotThrow(() -> controller.setSendCommentButton(document));
    }

    @Test
    void testAddTransaction_MissingDueDate() {
        dueDate.setValue(null);

        assertDoesNotThrow(() -> controller.addTransactionToDatabase(document, stage));
    }

    @Test
    void testAddTransaction_InvalidDueDate() {
        dueDate.setValue(LocalDate.now().plusDays(10));

        assertDoesNotThrow(() -> controller.addTransactionToDatabase(document, stage));
    }

    @Test
    void testAddTransaction_OutOfStock() {
        Document outDoc = new Document(
                3,
                "Database Systems",
                "Nguyen Van A",
                "Technology",
                0,
                "db.png"
        );

        dueDate.setValue(LocalDate.now().plusDays(3));

        assertDoesNotThrow(() -> controller.addTransactionToDatabase(outDoc, stage));
    }

    @Test
    void testAddTransaction_ValidData() {
        dueDate.setValue(LocalDate.now().plusDays(3));

        assertDoesNotThrow(() -> controller.addTransactionToDatabase(document, stage));
    }
}
