package com.example.librarymanagementsystemapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class loginSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private TextField usernamefield;

    @FXML
    private Label loginlabel;

    @FXML
    private AnchorPane main_form;

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    public void switchToAdminScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToUserScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("userScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToRegistrationScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registrationScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void setLoginbutton(ActionEvent event) {
        loginlabel.setAlignment(Pos.CENTER);
        if (usernamefield.getText().isBlank() == false && passwordfield.getText().isBlank() == false) {

            Database connectNow = new Database();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT account_id, role FROM user_account WHERE username = ? AND password = ?";
            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
                preparedStatement.setString(1, usernamefield.getText());
                preparedStatement.setString(2, passwordfield.getText());
                ResultSet queryResult = preparedStatement.executeQuery();

                if(queryResult.next()) {
                    int userId = queryResult.getInt("account_id");
                    String role = queryResult.getString("role");

                    UserSceneController.getInstance().setUserId(userId);
                    if ("admin".equals(role)) {
                        switchToAdminScene(event);
                    } else {
                        switchToUserScene(event);
                    }
                } else {
                    loginlabel.setText("Invalid login. Please try again!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            loginlabel.setText("Please enter username and password.");
        }
    }
}
