package com.example.librarymanagementsystemapp;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class loginSceneControllerTest extends JavaFXTestBase {

    private loginSceneController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new loginSceneController();

        setField("usernamefield", new TextField());
        setField("passwordfield", new PasswordField());
        setField("loginlabel", new Label());
        setField("main_form", new AnchorPane());
    }

    private void setField(String name, Object value) throws Exception {
        Field f = loginSceneController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, value);
    }

    private <T> T getField(String name, Class<T> type) throws Exception {
        Field f = loginSceneController.class.getDeclaredField(name);
        f.setAccessible(true);
        return type.cast(f.get(controller));
    }

    private ActionEvent mockEvent(Stage stage) {
        Node node = mock(Node.class);
        Scene scene = new Scene(new AnchorPane());

        when(node.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);

        return new ActionEvent(node, null);
    }

    @Test
    void testEmptyFieldsShowsWarning() throws Exception {
        TextField user = getField("usernamefield", TextField.class);
        PasswordField pass = getField("passwordfield", PasswordField.class);
        Label label = getField("loginlabel", Label.class);

        user.setText("");
        pass.setText("");

        controller.setLoginbutton(new ActionEvent());

        assertEquals("Please enter username and password.", label.getText());
    }

    @Test
    void testInvalidLoginShowsError() throws Exception {
        TextField user = getField("usernamefield", TextField.class);
        PasswordField pass = getField("passwordfield", PasswordField.class);
        Label label = getField("loginlabel", Label.class);

        user.setText("admin");
        pass.setText("wrongpass");

        Database mockDb = mock(Database.class);
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockDb.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        try (var mocked = Mockito.mockConstruction(Database.class,
                (mock, ctx) -> when(mock.getConnection()).thenReturn(mockConn))) {

            controller.setLoginbutton(new ActionEvent());
        }

        assertEquals("Invalid login. Please try again!", label.getText());
    }

    @Test
    void testValidAdminLoginRedirects() throws Exception {
        TextField user = getField("usernamefield", TextField.class);
        PasswordField pass = getField("passwordfield", PasswordField.class);

        user.setText("admin");
        pass.setText("123");

        Database mockDb = mock(Database.class);
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockDb.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenReturn(mockRs);

        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("account_id")).thenReturn(5);
        when(mockRs.getString("role")).thenReturn("admin");

        UserSceneController fakeUser = mock(UserSceneController.class);
        Field inst = UserSceneController.class.getDeclaredField("instance");
        inst.setAccessible(true);
        inst.set(null, fakeUser);

        Stage stage = new Stage();
        ActionEvent event = mockEvent(stage);

        loginSceneController spyController = Mockito.spy(controller);
        doNothing().when(spyController).switchToAdminScene(any());

        try (var mocked = Mockito.mockConstruction(Database.class,
                (mock, ctx) -> when(mock.getConnection()).thenReturn(mockConn))) {

            spyController.setLoginbutton(event);
        }

        verify(fakeUser).setUserId(5);
        verify(spyController).switchToAdminScene(any());
    }

    @Test
    void testValidUserLoginRedirects() throws Exception {
        TextField user = getField("usernamefield", TextField.class);
        PasswordField pass = getField("passwordfield", PasswordField.class);

        user.setText("user");
        pass.setText("123");

        Database mockDb = mock(Database.class);
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockDb.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenReturn(mockRs);

        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("account_id")).thenReturn(8);
        when(mockRs.getString("role")).thenReturn("user");

        UserSceneController fakeUser = mock(UserSceneController.class);
        Field inst = UserSceneController.class.getDeclaredField("instance");
        inst.setAccessible(true);
        inst.set(null, fakeUser);

        Stage stage = new Stage();
        ActionEvent event = mockEvent(stage);

        loginSceneController spyController = Mockito.spy(controller);
        doNothing().when(spyController).switchToUserScene(any());

        try (var mocked = Mockito.mockConstruction(Database.class,
                (mock, ctx) -> when(mock.getConnection()).thenReturn(mockConn))) {

            spyController.setLoginbutton(event);
        }

        verify(fakeUser).setUserId(8);
        verify(spyController).switchToUserScene(any());
    }
}
