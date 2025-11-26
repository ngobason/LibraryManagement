package com.example.librarymanagementsystemapp;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;



public class DocumentController {

    @FXML
    private Label documentAuthor;

    @FXML
    private ImageView documentImage;

    @FXML
    private Label documentName;

    @FXML
    private Button addButton;

    public void setData (Document document) {
        Image image = new Image(document.getImage());
        documentImage.setImage(image);
        documentName.setText(document.getTitle());
        documentAuthor.setText(document.getAuthor());

        addButton.setOnAction(event-> setAddButton(document));
    }

    public void setAddButton(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("documentDetails.fxml"));
            Parent root = loader.load();

            Stage detailStage = new Stage();
            detailStage.setTitle("Book Details");
            detailStage.setScene(new Scene(root));

            DocumentDetailsController controller = loader.getController();
            controller.setDocument(document, detailStage);
            detailStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
