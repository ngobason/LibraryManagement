package com.example.librarymanagementsystemapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CardController {
    @FXML
    private Label Author;

    @FXML
    private Label Genre;

    @FXML
    private HBox box;

    @FXML
    private ImageView documentImage;

    @FXML
    private Label title;

    @FXML
    private Button borrowButton;


    private String [] colors = {"B9E5EF", "BDB2FE", "FB9AA8", "FF5056","#FFFF99","F5CAFF","A8E4FF"};

    public void setData(Document document) {
        Image image = new Image(document.getImage());
        documentImage.setImage(image);
        title.setText(document.getTitle());
        Author.setText(document.getAuthor());
        Genre.setText(document.getGenre());

        box.setStyle("-fx-background-color: " + colors[(int)(Math.random()*colors.length)]);
        borrowButton.setOnAction(event->setBorrowButton(document));
    }

    public void setBorrowButton(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("documentDetails2.fxml"));
            Parent root = loader.load();
            Stage detailStage = new Stage();
            detailStage.setTitle("Book Details");
            detailStage.setScene(new Scene(root));

            DocumentDetails2Controller controller = loader.getController();
            controller.setDocument(document, detailStage);

            detailStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
