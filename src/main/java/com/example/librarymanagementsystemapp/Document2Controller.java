package com.example.librarymanagementsystemapp;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Document2Controller {

    @FXML
    private Label documentAuthor;

    @FXML
    private ImageView documentImage;

    @FXML
    private Label documentName;

    @FXML
    private Button borrowButton;

    public void setData (Document document) {
        Image image = new Image(document.getImage());
        documentImage.setImage(image);
        documentName.setText(document.getTitle());
        documentAuthor.setText(document.getAuthor());

        borrowButton.setOnAction(event-> setAddButton(document));
    }

    public void setAddButton(Document document) {
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
