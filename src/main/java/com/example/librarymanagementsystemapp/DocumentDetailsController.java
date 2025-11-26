package com.example.librarymanagementsystemapp;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DocumentDetailsController {

    @FXML
    private TextField documentAuthor;

    @FXML
    private TextField documentGenre;

    @FXML
    private TextField documentId;

    @FXML
    private ImageView documentImage;

    @FXML
    private TextField documentQuantity;

    @FXML
    private TextField documentTitle;

    @FXML
    private Button addButton;

    public void setDocument(Document document, Stage stage) {
        documentTitle.setText(document.getTitle());
        documentAuthor.setText(document.getAuthor());
        documentGenre.setText((document.getGenre()));
        if (document.getImage() != null) {
            documentImage.setImage(new Image(document.getImage()));
        }
        addButton.setOnAction(event -> setAddButton(document, stage));
    }

    public void addDocumentToDataBase(Document document, Stage stage) {
        String sql = "INSERT INTO document (document_id, title, author, quantity, image, genre) VALUES(?,?,?,?,?,?)";

        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            if (documentId.getText().isEmpty()
                    || documentTitle.getText().isEmpty()
                    || documentAuthor.getText().isEmpty()
                    || documentGenre.getText().isEmpty()
                    || documentQuantity.getText().isEmpty()) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing Information");
                    alert.setContentText("Please enter all required information in each field!");
                    alert.showAndWait();
                });
                return;
            } else {
                int id = 0;
                try {
                    id = Integer.parseInt(documentId.getText());
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid ID");
                        alert.setContentText("The ID must be a valid integer");
                        alert.showAndWait();
                    });
                    return;
                }

                String checkID = "SELECT document_id FROM document WHERE document_id = '" + documentId.getText() + "'";
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(checkID);
                if (result.next()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Duplicate ID");
                        alert.setContentText("This ID already exists. Please enter a different ID.");
                        alert.showAndWait();
                    });
                    return;
                }


                int quantity = 0;
                try {
                    quantity = Integer.parseInt(documentQuantity.getText());
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Quantity");
                        alert.setContentText("The quantity must be a valid integer");
                        alert.showAndWait();
                    });
                    return;
                }
                if (quantity <= 0) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Quantity");
                        alert.setHeaderText(null);
                        alert.setContentText("The quantity must be greater than 0.");
                        alert.showAndWait();
                    });
                    return;
                }
                PreparedStatement prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.setString(2, documentTitle.getText());
                prepare.setString(3, documentAuthor.getText());
                prepare.setInt(4, quantity);
                prepare.setString(5, document.getImage());
                prepare.setString(6, documentGenre.getText());
                prepare.executeUpdate();

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Book was added to the library successfully!");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            stage.close();
                        }
                    });
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAddButton(Document document, Stage stage) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                addDocumentToDataBase(document, stage);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
