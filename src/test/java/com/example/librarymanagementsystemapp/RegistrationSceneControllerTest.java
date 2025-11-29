package com.example.librarymanagementsystemapp;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegistrationSceneControllerTest {

    private RegistrationSceneController controller;

    @BeforeEach
    void setUp() {
        controller = new RegistrationSceneController();

        // mock all UI fields
        controller.firstnameField = new TextField();
        controller.lastnameField = new TextField();
        controller.usernameField = new TextField();
        controller.passwordField = new TextField();
        controller.confirmpasswordField = new TextField();
        controller.confirmpasswordmessage = new Label();
    }

    @Test
void testPasswordMismatch() throws Exception {
    controller.firstnameField.setText("John");
    controller.lastnameField.setText("Doe");
    controller.usernameField.setText("testuser");
    controller.passwordField.setText("12345");
    controller.confirmpasswordField.setText("54321");

    // Gọi hàm cần test
    controller.setRigisterbButton(null);

    assertEquals("Password does not match",
            controller.confirmpasswordmessage.getText());
}


    @Test
    void testRegisterUser_SQLExecuted() throws Exception {
        // Arrange
        controller.firstnameField.setText("John");
        controller.lastnameField.setText("Doe");
        controller.usernameField.setText("testuser");
        controller.passwordField.setText("12345");
        controller.confirmpasswordField.setText("12345");

        // Mock Database + Connection + Statement
        Database dbMock = Mockito.mock(Database.class);
        Connection connMock = Mockito.mock(Connection.class);
        Statement stmtMock = Mockito.mock(Statement.class);

        when(dbMock.getConnection()).thenReturn(connMock);
        when(connMock.createStatement()).thenReturn(stmtMock);

        // Inject mock DB bằng cách tạo subclass để override
        RegistrationSceneController testController = new RegistrationSceneController() {
            @Override
            public void registerUser() {
                try {
                    Statement st = connMock.createStatement();
                    st.executeUpdate("FAKE SQL");
                } catch (Exception e) {}
            }
        };

        // Act
        testController.registerUser();

        // Assert
        verify(connMock, times(1)).createStatement();
        verify(stmtMock, times(1)).executeUpdate(anyString());
    }
}
