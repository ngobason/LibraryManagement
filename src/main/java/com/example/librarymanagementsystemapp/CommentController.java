package com.example.librarymanagementsystemapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Circle;


import java.net.URL;
import java.util.ResourceBundle;


public class CommentController implements Initializable {
    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label comment;

    public void setData (Comment comment1) {
        String uri = "file:" + comment1.getImage();
        Image image2 = new Image(uri);
        image.setImage(image2);
        name.setText(comment1.getName());
        comment.setText(comment1.getComment());
    }

    public void setImage() {
        double radius = image.getFitHeight()/2;
        Circle clip = new Circle(radius);
        clip.setCenterX(image.getFitHeight()/2);
        clip.setCenterY(image.getFitWidth()/2);
        image.setClip(clip);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImage();
    }
}
