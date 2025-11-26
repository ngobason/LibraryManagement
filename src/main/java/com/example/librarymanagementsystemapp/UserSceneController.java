package com.example.librarymanagementsystemapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class UserSceneController implements Initializable {

    public int idUser;
    private String path;
    private String[] genderList ={"Male", "Female", "Other"};
    ObservableList<MyDocument> myDocumentList;


    @FXML
    private HBox cardLayOut;

    @FXML
    private Button home_button;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField searchDocument;

    @FXML
    private GridPane documentContainer;

    @FXML
    private TableColumn<MyDocument, Void> myDocument_action;

    @FXML
    private TableColumn<MyDocument, String> myDocument_author;

    @FXML
    private TableColumn<MyDocument, Date> myDocument_borrowDate;

    @FXML
    private TableColumn<MyDocument, Date> myDocument_dueDate;

    @FXML
    private TableColumn<MyDocument, String> myDocument_genre;

    @FXML
    private TableColumn<MyDocument, Integer> myDocument_id;

    @FXML
    private TableColumn<MyDocument, String> myDocument_name;

    @FXML
    private TableView<MyDocument> myDocument_table;

    @FXML
    private AnchorPane myDocument_form;

    @FXML
    private Button myDocument_button;

    @FXML
    private Button profile_button;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private ImageView welcomeImageUser;

    @FXML
    private Label welcomeUserName;

    @FXML
    private ImageView profile_imageUser;

    @FXML
    private Button profile_importButton;

    @FXML
    private TextField profile_userName;

    @FXML
    private TextField profile_firstName;

    @FXML
    private TextField profile_lastName;

    @FXML
    private ComboBox profile_gender;

    @FXML
    private TextField profile_gmail;

    @FXML
    private TextField profile_phone;

    @FXML
    private Button profile_updateButton;

    @FXML
    private TextField profile_currentPassword;

    @FXML
    private TextField profile_newPassword;

    @FXML
    private TextField profile_confirmPassword;

    @FXML
    private Label labelWrongPassword;

    @FXML
    private Label labelWrongConfirm;


    private static UserSceneController instance;

    public UserSceneController() {
    }

    public static UserSceneController getInstance() {
        if (instance == null) {
            instance = new UserSceneController();
        }
        return instance;
    }

    public void setUserId(int id) {
        this.idUser = id;
    }

    public int getUserId() {
        return idUser;
    }

    @FXML
    public void switchLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logout(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            switchLoginScene(event);
        }
    }

    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_button) {
            home_form.setVisible(true);
            myDocument_form.setVisible(false);
            profile_form.setVisible(false);

            home_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            myDocument_button.setStyle("-fx-background-color:transparent");
            profile_button.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == myDocument_button) {
            home_form.setVisible(false);
            myDocument_form.setVisible(true);
            profile_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            myDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            profile_button.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == profile_button) {
            home_form.setVisible(false);
            myDocument_form.setVisible(false);
            profile_form.setVisible(true);

            home_button.setStyle("-fx-background-color:transparent");
            myDocument_button.setStyle("-fx-background-color:transparent");
            profile_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
        }
    }

    private List<Document> recentlyAdded() {
        List<Document> recentBooks = new ArrayList<>();
        String sql = "SELECT * FROM document ORDER BY document_id DESC LIMIT 6";

        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try (Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Document document = new Document(
                        result.getInt("document_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getInt("quantity"),
                        result.getString("image")
                );
                recentBooks.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentBooks;
    }
    private  List<Document> recentlyAdded;

    public void showRecentlyAdded() {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try {
            cardLayOut.setSpacing(20);
            for (int i = 0;i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayOut.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List <Document> searchDocuments(String keyword) {
        List<Document> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM document WHERE title LIKE ? OR author LIKE ?";

        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Document document = new Document(
                        result.getInt("document_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getInt("quantity"),
                        result.getString("image")
                );
                searchResults.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public void setSearchDocumentButton() {
        String keyword = searchDocument.getText().trim();
        if (keyword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a keyword to search!");
            alert.showAndWait();
            return;
        }

        Task<List<Document>> task = new Task<>() {
            @Override
            protected List<Document> call() throws Exception {
                return searchDocuments(keyword);
            }
        };

        task.setOnSucceeded(e-> {
            showSearchDocument(task.getValue());
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void showSearchDocument(List<Document> search) {
        int column = 0;
        int row = 1;

        documentContainer.getChildren().clear();

        try {
            for (Document document : search) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("document2.fxml"));
                VBox documentBox = fxmlLoader.load();
                Document2Controller documentController = fxmlLoader.getController();
                documentController.setData(document);
                if(column == 5) {
                    column = 0;
                    ++row;
                }

                documentContainer.add(documentBox, column++, row);
                GridPane.setMargin(documentBox, new Insets(7,15,20,25));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTopBooksInContainer(List<Document> topBooks) {
        documentContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try {
            for (Document document : topBooks) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("document2.fxml"));
                VBox documentBox = fxmlLoader.load();

                Document2Controller documentController2 = fxmlLoader.getController();
                documentController2.setData(document);

                if (column == 5) {
                    column = 0;
                    ++row;
                }

                documentContainer.add(documentBox, column++, row);
                GridPane.setMargin(documentBox, new Insets(7, 15, 20, 25));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Document> getTop10MostBorrowedBooks()  {
        List<Document> topBooks = new ArrayList<>();
        String sql = "SELECT d.document_id, d.title, d.image, d.genre, d.quantity,d.author , COUNT(bt.document_id) AS borrow_count\n" +
                "                 FROM borrowtransaction bt \n" +
                "                 JOIN document d ON bt.document_id = d.document_id \n" +
                "                 GROUP BY bt.document_id \n" +
                "                 ORDER BY borrow_count DESC LIMIT 10;";

        Database connectNow = new Database();
        Connection connection = connectNow.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Document document = new Document(
                        resultSet.getInt("document_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("image")
                );
                topBooks.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topBooks;
    }

    public void showMyDocumentList() {
        Task<ObservableList<MyDocument>> task = new Task<ObservableList<MyDocument>>() {
            @Override
            protected ObservableList<MyDocument> call() throws Exception {
                myDocumentList = FXCollections.observableArrayList();

                String sql = "SELECT bt.borrowtransaction_id,d.document_id,d.title as document_name, d.author, d.genre, bt.borrow_date, bt.due_date\n" +
                        "FROM borrowtransaction bt\n" +
                        "JOIN user_account us ON us.account_id = bt.account_id\n" +
                        "JOIN document d ON d.document_id = bt.document_id\n" +
                        "WHERE bt.status = 'Borrowing' AND us.account_id = '" + UserSceneController.getInstance().getUserId() + "'";


                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();

                try {
                    Statement statement = connect.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()) {
                        MyDocument myDocument = new MyDocument(
                                resultSet.getInt("borrowtransaction_id"),
                                resultSet.getInt("document_id"),
                                resultSet.getString("document_name"),
                                resultSet.getString("author"),
                                resultSet.getString("genre"),
                                resultSet.getDate("borrow_date"),
                                resultSet.getDate("due_date")
                        );
                        myDocumentList.add(myDocument);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return myDocumentList;
            }
        };
        task.setOnSucceeded(even-> {
            ObservableList<MyDocument> myDocuments = task.getValue();

            myDocument_id.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
            myDocument_name.setCellValueFactory(new PropertyValueFactory<>("documentName"));
            myDocument_author.setCellValueFactory(new PropertyValueFactory<>("documentAuthor"));
            myDocument_genre.setCellValueFactory(new PropertyValueFactory<>("documentGenre"));
            myDocument_borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            myDocument_dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

            myDocument_action.setCellFactory(col -> new TableCell<>() {
                private final Button actionButton = new Button("Return");
                {
                    actionButton.getStyleClass().add("add-btn");
                    actionButton.setOnAction(event-> {
                        MyDocument myDocument = getTableView().getItems().get(getIndex());
                        setActionButton(myDocument);
                    });
                }

                @Override
                protected void updateItem(Void unused, boolean b) {
                    super.updateItem(unused, b);
                    if(b) {
                        setGraphic(null);
                    } else {
                        setGraphic(actionButton);
                    }
                }
            });
            myDocument_table.setItems(myDocuments);
            myDocument_id.setResizable(false);
            myDocument_name.setResizable(false);
            myDocument_author.setResizable(false);
            myDocument_genre.setResizable(false);
            myDocument_genre.setResizable(false);
            myDocument_dueDate.setResizable(false);
            myDocument_action.setResizable(false);
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void setActionButton(MyDocument myDocument) {

        LocalDate today = LocalDate.now();
        LocalDate dueDate = myDocument.getDueDate().toLocalDate();

        Database connectNow = new Database();
        Connection connection = connectNow.getConnection();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to return this book ?");

        alert.showAndWait().ifPresent(respone -> {
            if (respone == ButtonType.OK) {
                try {
                    String updateQuery = "UPDATE borrowtransaction SET status = 'Returned', return_date = CURDATE() WHERE borrowtransaction_id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, myDocument.getTransactionId());
                    updateStatement.executeUpdate();
                    String updateQuantityQuery = "UPDATE document SET quantity = quantity + 1 WHERE document_id = ?";
                    PreparedStatement updateQuantityStatement = connection.prepareStatement(updateQuantityQuery);
                    updateQuantityStatement.setInt(1, myDocument.getDocumentId());
                    updateQuantityStatement.executeUpdate();


                    myDocument_table.getItems().remove(myDocument);

                    if (today.isAfter(dueDate)) {
                        Alert lateAlert = new Alert(Alert.AlertType.WARNING);
                        lateAlert.setTitle("Late return");
                        lateAlert.setContentText(null);
                        lateAlert.setContentText("This book is returned late! Due date was: " + dueDate);
                        lateAlert.showAndWait();
                    } else {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("This book has been returned successfully!");
                        successAlert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setImportButton() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));

        Stage stage = (Stage)profile_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profile_imageUser.setImage(image);

            path = file.getAbsolutePath();
        }
    }

    public void setGenderList() {
        List<String> listData = new ArrayList<>();

        for(String data : genderList) {
            listData.add(data);
        }

        ObservableList listD = FXCollections.observableArrayList(listData);
        profile_gender.setItems(listD);
    }

    public void setImage() {
        double radius2 = profile_imageUser.getFitHeight()/2;
        Circle clip2 = new Circle(radius2);
        clip2.setCenterX(profile_imageUser.getFitHeight()/2);
        clip2.setCenterY(profile_imageUser.getFitWidth()/2);
        profile_imageUser.setClip(clip2);


        double radius = welcomeImageUser.getFitHeight()/2;
        Circle clip = new Circle(radius);
        clip.setCenterX(welcomeImageUser.getFitHeight()/2);
        clip.setCenterY(welcomeImageUser.getFitWidth()/2);
        welcomeImageUser.setClip(clip);
    }

    public String getAccountImage() {
        String imagePath = null;
        String sql =  "SELECT image FROM user_account WHERE account_id = " + UserSceneController.getInstance().getUserId();
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                imagePath = resultSet.getString("image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imagePath != null && !imagePath.isEmpty()) {
            imagePath = "file:" + imagePath;
        }
        return imagePath;
    }

    public void setDefaultProfile() {
        String sql =  "SELECT * FROM user_account where account_id = '" + UserSceneController.getInstance().getUserId() + "'";
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                profile_userName.setText(resultSet.getString("username"));
                profile_firstName.setText(resultSet.getString("firstname"));
                profile_lastName.setText(resultSet.getString("lastname"));
                profile_gender.setValue(resultSet.getString("gender"));
                profile_gmail.setText(resultSet.getString("gmail"));
                profile_phone.setText(resultSet.getString("phone"));
                welcomeUserName.setText(resultSet.getString("firstname"));


                if (getAccountImage() != null && !getAccountImage().isEmpty()) {
                    Image image = new Image(getAccountImage());
                    welcomeImageUser.setImage(image);
                    profile_imageUser.setImage(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpdateButton() {

        if (profile_gender.getValue() != null) {
            String selectedValue = (String) profile_gender.getSelectionModel().getSelectedItem();
            if (path == null) {
                if (selectedValue.equals("Male")) {
                    path = "D:/Code/Java/LibraryManagementSystemApp/src/main/resources/com/example/librarymanagementsystemapp/Image/boy.png";
                } else if (selectedValue.equals("Female")) {
                    path = "D:/Code/Java/LibraryManagementSystemApp/src/main/resources/com/example/librarymanagementsystemapp/Image/woman.png";
                } else {
                    path = "D:/Code/Java/LibraryManagementSystemApp/src/main/resources/com/example/librarymanagementsystemapp/Image/profile-user.png";
                }
            } else {
                path = path.replace("\\","/");
            }
            String sql = "UPDATE user_account SET firstname = '"
                    + profile_firstName.getText() + "', lastname = '"
                    + profile_lastName.getText() + "', gender = '"
                    + selectedValue + "', gmail = '"
                    + profile_gmail.getText() + "', phone = '"
                    + profile_phone.getText() + "', image = '"
                    + path + "' WHERE account_id = '"
                    + UserSceneController.getInstance().getUserId() + "'";
            Database connectNow = new Database();
            Connection connect = connectNow.getConnection();

            try {
                Alert alert;

                if (profile_firstName.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing Name");
                    alert.setContentText("Please enter your first name");
                    alert.showAndWait();
                } else {
                    Statement statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Profile Updated");
                    alert.setHeaderText(null);
                    alert.setContentText("Your profile has been successfully updated!");
                    alert.showAndWait();
                    Image image = new Image(getAccountImage());
                    welcomeImageUser.setImage(image);
                    profile_imageUser.setImage(image);
                    welcomeUserName.setText(profile_firstName.getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changePassWord() {
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        if(profile_currentPassword.getText().isBlank() || profile_newPassword.getText().isBlank() || profile_confirmPassword.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all the fields");
            alert.showAndWait();
            return;
        }
        else {
            String verifyLogin = "SELECT password FROM user_account WHERE account_id = " + UserSceneController.getInstance().getUserId();

            try {
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(verifyLogin);
                if (resultSet.next()) {
                    System.out.println(profile_currentPassword.getText());
                    System.out.println(resultSet.getString("password"));
                    if (profile_currentPassword.getText().equals(resultSet.getString("password"))) {
                        if (profile_newPassword.getText().equals(profile_confirmPassword.getText())) {
                            String sql = "UPDATE user_account set password = '" + profile_newPassword.getText() + "' WHERE account_id = " + UserSceneController.getInstance().getUserId();

                            Statement statement1 = connect.createStatement();
                            statement1.executeUpdate(sql);

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Your password has been changed successfully!");
                            alert.showAndWait();

                            labelWrongConfirm.setVisible(false);
                            labelWrongPassword.setVisible(false);
                            profile_currentPassword.clear();
                            profile_newPassword.clear();
                            profile_confirmPassword.clear();

                        } else {
                            labelWrongConfirm.setVisible(true);
                            labelWrongPassword.setVisible(false);
                        }
                    } else {
                        labelWrongPassword.setVisible(true);
                        labelWrongConfirm.setVisible(false);
                        return;
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRecentlyAdded();
        showMyDocumentList();
        setImage();
        setGenderList();
        setDefaultProfile();
        showTopBooksInContainer(getTop10MostBorrowedBooks());
    }
}
