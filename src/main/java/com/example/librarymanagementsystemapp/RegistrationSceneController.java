package com.example.librarymanagementsystemapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class RegistrationSceneController {
    public Stage stage;
    public Scene scene;
    public Parent root;

    @FXML
    TextField confirmpasswordField;

    @FXML
    TextField firstnameField;

    @FXML
    TextField lastnameField;

    @FXML
    TextField passwordField;

    @FXML
    TextField usernameField;

    @FXML
    Label confirmpasswordmessage;

    @FXML
    AnchorPane main_form;

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void switchLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void setRigisterbButton(ActionEvent event) throws IOException {
        if (firstnameField.getText().isBlank() == false
                && lastnameField.getText().isBlank() == false
                && usernameField.getText().isBlank() == false
                && passwordField.getText().isBlank() == false
                && confirmpasswordField.getText().isBlank() == false) {
            if (passwordField.getText().equals(confirmpasswordField.getText())) {
                registerUser();
                confirmpasswordmessage.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been registered succesfully!", ButtonType.OK);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            switchLoginScene(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                confirmpasswordmessage.setText("Password does not match");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all required fields.", ButtonType.OK);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void registerUser() {
        Database connectNow = new Database();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String image = "D:/Code/Java/LibraryManagementSystemApp/src/main/resources/com/example/librarymanagementsystemapp/Image/profile-user.png";


        String insertFields = "INSERT INTO user_account(firstname, lastname, username, password, image) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "','" + image + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
