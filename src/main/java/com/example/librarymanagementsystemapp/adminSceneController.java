package com.example.librarymanagementsystemapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class adminSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private ObservableList<Document> manageDocumentList;
    private ObservableList<User> userList;
    private ObservableList<BorrowTransaction> requestList;
    private ObservableList<BorrowTransaction> viewRecordList;
    private int checkViewRecords = 0;
    private static final int AllRecords = 0;
    private static final int IssuedList = 1;
    private static final int DefaulterList = 2;

    @FXML
    private AnchorPane add_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addDocument_button;

    @FXML
    private TableView<Document> manage_table;

    @FXML
    private TableColumn<Document, String> author_column;

    @FXML
    private Button home_button;

    @FXML
    private TableColumn<Document, Integer> id_column;

    @FXML
    private TableColumn<Document, Integer> quantity_column;

    @FXML
    private TableColumn<Document, String> title_column;

    @FXML
    private TableColumn<Document, String> genre_column;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button viewRecords_button;

    @FXML
    private AnchorPane viewRecords_form;

    @FXML
    private Button allRecords_button;

    @FXML
    private Button defaulterList_button;

    @FXML
    private TableView<BorrowTransaction> tableView_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Integer> idColumn_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> borrowerName_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> documentName_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> borrowDate_ViewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> dueDate_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, String> status_viewRecords;

    @FXML
    private TableColumn<BorrowTransaction, Date> returnDate_viewRecords;


    @FXML
    private Label availableDocuments_home;

    @FXML
    private Label issuedDocument_home;

    @FXML
    private GridPane documentContainer2;

    @FXML
    private TextField searchDocument;

    @FXML
    private AnchorPane addDocument_form;

    @FXML
    private AnchorPane manageDocument_form;

    @FXML
    private Button manageDocument_button;

    @FXML
    private ImageView manage_image;

    @FXML
    private TextField manage_id;

    @FXML
    private TextField manage_title;

    @FXML
    private TextField manage_author;

    @FXML
    private TextField manage_genre;

    @FXML
    private TextField manage_quantity;

    @FXML
    private TextField manage_search;

    @FXML
    private Button approvalRequest_button;

    @FXML
    private AnchorPane approvalRequest_form;

    @FXML
    private TableView<BorrowTransaction> request_table;

    @FXML
    private TableColumn<BorrowTransaction, String> request_idColumn;

    @FXML
    private TableColumn<BorrowTransaction, String> request_documentName;

    @FXML
    private TableColumn<BorrowTransaction, String> request_borrowerName;

    @FXML
    private TableColumn<BorrowTransaction, Date> request_borrowDate;

    @FXML
    private TableColumn<BorrowTransaction, Date> request_dueDate;

    @FXML
    private TableColumn<BorrowTransaction, String> request_statusColumn;

    @FXML
    private TableColumn<BorrowTransaction, Boolean> request_selectColumn;

    @FXML
    private AnchorPane manageUsers_form;

    @FXML
    private TextField user_search;

    @FXML
    private TableView<User> users_table;

    @FXML
    private TableColumn<User, Integer> userId_column;

    @FXML
    private TableColumn<User, String> userUsername_column;

    @FXML
    private TableColumn<User, String> userFullName_column;

    @FXML
    private TableColumn<User, String> userGender_column;

    @FXML
    private TableColumn<User, String> userPhone_column;

    @FXML
    private TableColumn<User, String> userGmail_column;

    @FXML
    private Button manageUsers_button;

    @FXML
    private PieChart home_pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label totalUsers_home;

    @FXML
    public void switchLoginScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_button) {
            home_form.setVisible(true);
            addDocument_form.setVisible(false);
            manageDocument_form.setVisible(false);
            approvalRequest_form.setVisible(false);
            manageUsers_form.setVisible(false);
            viewRecords_form.setVisible(false);


            home_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            addDocument_button.setStyle("-fx-background-color:transparent");
            manageDocument_form.setStyle("-fx-background-color:transparent");
            approvalRequest_button.setStyle("-fx-background-color:transparent");
            manageUsers_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            availableDocuments_home();
            issuedDocuments_home();
        } else if (event.getSource() == addDocument_button) {
            home_form.setVisible(false);
            addDocument_form.setVisible(true);
            manageDocument_form.setVisible(false);
            approvalRequest_form.setVisible(false);
            manageUsers_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            manageDocument_button.setStyle("-fx-background-color:transparent");
            approvalRequest_button.setStyle("-fx-background-color:transparent");
            manageUsers_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == manageDocument_button) {
            home_form.setVisible(false);
            addDocument_form.setVisible(false);
            manageDocument_form.setVisible(true);
            approvalRequest_form.setVisible(false);
            manageUsers_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            manageDocument_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            approvalRequest_button.setStyle("-fx-background-color:transparent");
            manageUsers_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            showListDocument();
            manage_search.clear();
            searchDocument();
        } else if (event.getSource() == approvalRequest_button) {
            home_form.setVisible(false);
            addDocument_form.setVisible(false);
            manageDocument_form.setVisible(false);
            approvalRequest_form.setVisible(true);
            manageUsers_form.setVisible(false);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            manageDocument_button.setStyle("-fx-background-color:transparent");
            approvalRequest_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            manageUsers_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            showRequestList();
        } else if (event.getSource() == manageUsers_button) {
            home_form.setVisible(false);
            addDocument_form.setVisible(false);
            manageDocument_form.setVisible(false);
            approvalRequest_form.setVisible(false);
            manageUsers_form.setVisible(true);
            viewRecords_form.setVisible(false);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            manageDocument_button.setStyle("-fx-background-color:transparent");
            approvalRequest_button.setStyle("-fx-background-color:transparent");
            manageUsers_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            viewRecords_button.setStyle("-fx-background-color:transparent");

            showUserList();
            searchDocument();
        } else if (event.getSource() == viewRecords_button) {
            home_form.setVisible(false);
            addDocument_form.setVisible(false);
            manageDocument_form.setVisible(false);
            approvalRequest_form.setVisible(false);
            manageUsers_form.setVisible(false);
            viewRecords_form.setVisible(true);

            home_button.setStyle("-fx-background-color:transparent");
            addDocument_button.setStyle("-fx-background-color:transparent");
            manageDocument_button.setStyle("-fx-background-color:transparent");
            approvalRequest_button.setStyle("-fx-background-color:transparent");
            manageUsers_button.setStyle("-fx-background-color:transparent");
            viewRecords_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");

            showBorrowTransactionList();
            if(checkViewRecords == AllRecords) {
                viewRecordList = getViewRecords();
            } else if(checkViewRecords == DefaulterList) {
                viewRecordList = getDefaulterList();
            }
            tableView_viewRecords.setItems(viewRecordList);;
        }
    }


    public void availableDocuments_home() {
        String sql = "SELECT COUNT(document_id) FROM document";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(document_id)");
            }
            availableDocuments_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void issuedDocuments_home() {
        String sql = "SELECT COUNT(DISTINCT document_id) FROM borrowtransaction";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(DISTINCT document_id)");
            }
            issuedDocument_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalUser_home() {
        String sql = "SELECT COUNT(account_id) FROM user_account WHERE role is null";

        Database connectNow = new Database();
        connect = connectNow.getConnection();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            int count = 0;
            while (result.next()) {
                count = result.getInt("COUNT(account_id)");
            }
            totalUsers_home.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Document> documents() {
        List<Document> documentList = new ArrayList<>();
        String sql = "SELECT d.document_id, d.title, d.author, d.quantity, d.image, d.genre, COUNT(bt.document_id) AS borrow_count " +
                "FROM document d " +
                "JOIN borrowtransaction bt ON d.document_id = bt.document_id " +
                "GROUP BY d.document_id, d.title, d.author, d.quantity, d.image, d.genre " +
                "ORDER BY borrow_count DESC " +
                "LIMIT 12";
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Document document = new Document(
                        result.getInt("document_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getInt("quantity"),
                        result.getString("image")
                );
                documentList.add(document);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentList;
    }


    public void search_GoogleAPI() {
        String query = searchDocument.getText();
        Task<List<Document>> task = new Task<>() {
            @Override
            protected List<Document> call() {
                List<String> queries = List.of(query);
                return GoogleBooksAPI.searchBooks(queries);
            }
        };
            task.setOnSucceeded(event -> {
                List<Document> books = task.getValue();
                documentContainer2.getChildren().clear(); // Xóa kết quả cũ
                int column = 0;
                int row = 1;
                for (Document document : books) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("document.fxml"));
                        VBox documentBox = fxmlLoader.load();
                        DocumentController documentController = fxmlLoader.getController();
                        documentController.setData(document);

                        if (column == 4) {
                            column = 0;
                            ++row;
                        }
                        documentContainer2.add(documentBox, column++, row);
                        GridPane.setMargin(documentBox, new Insets(0, 12, 20, 22));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            task.setOnFailed(event -> {
                Throwable exception = task.getException();
                exception.printStackTrace();
            });
    new Thread(task).start();
    }

    public void  showListDocument() {
        Task<ObservableList<Document>> task = new Task<ObservableList<Document>>() {
            @Override
            protected ObservableList<Document> call() throws Exception {
                manageDocumentList = FXCollections.observableArrayList();

                String sql = "SELECT * FROM document";
                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();

                try {
                    Statement statement = connect.createStatement();
                    ResultSet result = statement.executeQuery(sql);

                    while (result.next()) {
                        Document document = new Document(
                                result.getInt("document_id"),
                                result.getString("title"),
                                result.getString("author"),
                                result.getString("genre"),
                                result.getInt("quantity"),
                                result.getString("image"));
                        manageDocumentList.add(document);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Failed to load documents.");
                        alert.showAndWait();
                    });
                }
                return manageDocumentList;
            }
        };

        task.setOnSucceeded(event -> {
            ObservableList<Document> manageDocumentlist = task.getValue();
            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
            author_column.setCellValueFactory(new PropertyValueFactory<>("author"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            genre_column.setCellValueFactory(new PropertyValueFactory<>("genre"));
            manage_table.setItems(manageDocumentlist);

            id_column.setResizable(false);
            title_column.setResizable(false);
            author_column.setResizable(false);
            quantity_column.setResizable(false);
            genre_column.setResizable(false);
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void manageDocumentSelected() {
        manage_table.setOnMouseClicked(event -> {
            Document selectedDocument = manage_table.getSelectionModel().getSelectedItem();
            if (selectedDocument != null) {
                manage_id.setText(String.valueOf(selectedDocument.getId()));
                manage_title.setText(selectedDocument.getTitle());
                manage_author.setText(selectedDocument.getAuthor());
                manage_genre.setText(selectedDocument.getGenre());
                manage_quantity.setText(String.valueOf(selectedDocument.getQuantity()));
                Image image = new Image(selectedDocument.getImage());
                manage_image.setImage(image);
            }
        });
    }

    public void manage_clearButton() {
        manage_id.clear();
        manage_title.clear();
        manage_author.clear();
        manage_genre.clear();
        manage_quantity.clear();
    }

    public void deleteDocument() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String sql = "DELETE FROM document WHERE document_id = '" + manage_id.getText() + "'";
                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();
                try {
                    if (manage_id.getText().isEmpty()
                            || manage_title.getText().isEmpty()
                            || manage_author.getText().isEmpty()
                            || manage_genre.getText().isEmpty()
                            || manage_quantity.getText().isEmpty()) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Missing Information");
                                alert.setContentText("Please enter all required information in each field!");
                                alert.showAndWait();
                            });
                            return null;
                    } else {
                        int quantity;
                        int id;
                        try {
                            id = Integer.parseInt(manage_id.getText());
                        } catch (NumberFormatException e) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid ID");
                                alert.setContentText("The ID must be a valid integer");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        try {
                            quantity = Integer.parseInt(manage_quantity.getText());
                        } catch (NumberFormatException e) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid Quantity");
                                alert.setContentText("The quantity must be a valid integer");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        String checkID = "SELECT * FROM document WHERE document_id = '" + manage_id.getText() + "'";
                        Statement statement  = connect.createStatement();
                        ResultSet result = statement.executeQuery(checkID);

                        if (!result.next()) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Document Not Found");
                                alert.setContentText("The document with ID " + manage_id.getText() + " does not exist.");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to delete this document ?");
                            Optional<ButtonType> option = alert.showAndWait();
                            if (option.get().equals(ButtonType.OK)) {
                                try {
                                    PreparedStatement statement1 = connect.prepareStatement(sql);
                                    statement1.executeUpdate();
                                    showListDocument();
                                    manage_clearButton();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void updateDocument() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String sql = "UPDATE document SET title = '"
                        + manage_title.getText() + "', author = '"
                        + manage_author.getText() + "', quantity = '"
                        + manage_quantity.getText() + "', genre = '"
                        + manage_genre.getText() + "' WHERE document_id = '"
                        + manage_id.getText() + "'";
                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();
                try {
                    if (manage_id.getText().isEmpty()
                            || manage_title.getText().isEmpty()
                            || manage_author.getText().isEmpty()
                            || manage_genre.getText().isEmpty()
                            || manage_quantity.getText().isEmpty()) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Missing Information");
                            alert.setContentText("Please enter all required information in each field!");
                            alert.showAndWait();
                        });
                        return null;
                    } else {
                        int quantity;
                        int id;
                        try {
                            id = Integer.parseInt(manage_id.getText());
                        } catch (NumberFormatException e) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid ID");
                                alert.setContentText("The ID must be a valid integer");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        try {
                            quantity = Integer.parseInt(manage_quantity.getText());
                        } catch (NumberFormatException e) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid Quantity");
                                alert.setContentText("The quantity must be a valid integer");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        String checkID = "SELECT * FROM document WHERE document_id = '" + manage_id.getText() + "'";
                        Statement statement  = connect.createStatement();
                        ResultSet result = statement.executeQuery(checkID);

                        if (!result.next()) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Document Not Found");
                                alert.setContentText("The document with ID " + manage_id.getText() + " does not exist.");
                                alert.showAndWait();
                            });
                            return null;
                        }
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to update:\nDocument ID: " + manage_id.getText() + "\n"
                                    + "Title: " + manage_title.getText() + "\n"
                                    + "Author: " + manage_author.getText() + "\n"
                                    + "Genre: " + manage_genre.getText() + "\n"
                                    + "Quantity: " + manage_quantity.getText());
                            Optional<ButtonType> option = alert.showAndWait();
                            if (option.get().equals(ButtonType.OK)) {
                                try {
                                    PreparedStatement statement1 = connect.prepareStatement(sql);
                                    statement1.executeUpdate();
                                    showListDocument();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void searchDocument() {
        FilteredList<Document> filteredList = new FilteredList<>(manageDocumentList, b -> true);
        manage_search.textProperty().addListener((observable, oldValue, newValue) -> {
            Task<Void> searchTask = new Task<>() {
                @Override
                protected Void call() {
                    filteredList.setPredicate(document -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String searchKey = newValue.toLowerCase();
                        if (String.valueOf(document.getId()).toLowerCase().contains(searchKey)) {
                            return true;
                        } else if (document.getTitle().toLowerCase().contains(searchKey)) {
                            return true;
                        } else if (document.getAuthor().toLowerCase().contains(searchKey)) {
                            return true;
                        }
                        return false;
                    });
                    return null;
                }
            };
            new Thread(searchTask).start();
        });
        SortedList<Document> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(manage_table.comparatorProperty());
        manage_table.setItems(sortedList);
    }

    public void showRequestList() {
        Task<ObservableList<BorrowTransaction>> task = new Task<ObservableList<BorrowTransaction>>() {
            @Override
            protected ObservableList<BorrowTransaction> call() throws Exception {
                requestList = FXCollections.observableArrayList();

                String sql = "SELECT bt.borrowtransaction_id,d.title as document_name, CONCAT(us.lastname,' ',us.firstname) as borrower_name, bt.borrow_date, bt.due_date, bt.status\n" +
                        "FROM borrowtransaction bt\n" +
                        "JOIN user_account us ON us.account_id = bt.account_id\n" +
                        "JOIN document d ON d.document_id = bt.document_id\n" +
                        "WHERE bt.status = 'Pending'";

                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();

                try {
                    Statement statement = connect.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()) {
                        BorrowTransaction transaction = new BorrowTransaction(
                                resultSet.getInt("borrowtransaction_id"),
                                resultSet.getString("document_name"),
                                resultSet.getString("borrower_name"),
                                resultSet.getDate("borrow_date"),
                                resultSet.getDate("due_date"),
                                resultSet.getString("status")
                        );
                        requestList.add(transaction);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return requestList;
            }
        };
        task.setOnSucceeded(even-> {
            ObservableList<BorrowTransaction> transactions = task.getValue();

            request_idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
            request_documentName.setCellValueFactory(new PropertyValueFactory<>("documentName"));
            request_borrowerName.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
            request_borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            request_dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            request_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            request_selectColumn.setCellValueFactory(cellData -> cellData.getValue().getSelected());
            request_selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(request_selectColumn));
            request_table.setItems(transactions);

            request_idColumn.setResizable(false);
            request_documentName.setResizable(false);
            request_borrowerName.setResizable(false);
            request_borrowDate.setResizable(false);
            request_dueDate.setResizable(false);
            request_selectColumn.setResizable(false);
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void setAcceptButton() {
        ObservableList<BorrowTransaction> selectedItems = request_table.getItems()
                .filtered(BorrowTransaction::isSelected);
        if (selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select at least one to accept!");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Acceptance");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to accept ?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Database connectNow = new Database();
                Connection connection = connectNow.getConnection();

                for (BorrowTransaction transaction : selectedItems) {

                    String updateQuery = "UPDATE borrowtransaction SET status = 'Borrowing' WHERE borrowtransaction_id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, transaction.getTransactionId());
                    updateStatement.executeUpdate();
                }

                request_table.getItems().removeAll(selectedItems);

            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while processing your request.");
                errorAlert.showAndWait();
            }
        }
    }

    public void searchUser() {
        FilteredList<User> filteredList = new FilteredList<>(userList, b -> true);
        user_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(user-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(user.getId()).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (user.getUserName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (user.getFullName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<User> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(users_table.comparatorProperty());
        users_table.setItems(sortedList);
    }

    public void setAcceptAllButton() {
        ObservableList<BorrowTransaction> selectedItems = request_table.getItems();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Acceptance");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to accept all?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Database connectNow = new Database();
                Connection connection = connectNow.getConnection();

                for (BorrowTransaction transaction : selectedItems) {

                    String updateQuery = "UPDATE borrowtransaction SET status = 'Borrowing' WHERE borrowtransaction_id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, transaction.getTransactionId());
                    updateStatement.executeUpdate();
                }

                request_table.getItems().removeAll(selectedItems);

            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while processing your request.");
                errorAlert.showAndWait();
            }
        }
    }

    public void showUserList() {
        Task<ObservableList<User>> task = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() throws Exception {
                userList = FXCollections.observableArrayList();

                String sql = "SELECT account_id,username, CONCAT(firstname, ' ', lastname) as full_name, gmail, phone, gender from user_account WHERE role is null;";
                Database connectNow = new Database();
                Connection connect = connectNow.getConnection();

                try {
                    Statement statement = connect.createStatement();
                    ResultSet result = statement.executeQuery(sql);

                    while (result.next()) {
                        String phone = result.getString("phone");
                        String gender = result.getString("gender");
                        String gmail = result.getString("gmail");

                        if (phone != null && phone.equals("null")) {
                            phone = "";
                        }
                        if (gender != null && gender.equals("null")) {
                            gender ="";
                        }
                        if (gmail != null && gmail.equals("null")) {
                            gmail = "";
                        }


                        User user = new User(
                                result.getInt("account_id"),
                                result.getString("username"),
                                result.getString("full_name"),
                                phone, gender, gmail);
                        userList.add(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Failed to load documents.");
                        alert.showAndWait();
                    });
                }
                return userList;
            }
        };

        task.setOnSucceeded(event -> {
            ObservableList<User> userList1 = task.getValue();
            userId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            userUsername_column.setCellValueFactory(new PropertyValueFactory<>("userName"));
            userFullName_column.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            userGender_column.setCellValueFactory(new PropertyValueFactory<>("gender"));
            userPhone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
            userGmail_column.setCellValueFactory(new PropertyValueFactory<>("gmail"));
            users_table.setItems(userList1);

            userId_column.setResizable(false);
            userUsername_column.setResizable(false);
            userFullName_column.setResizable(false);
            userGender_column.setResizable(false);
            userPhone_column.setResizable(false);
            userGmail_column.setResizable(false);
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void deleteUser() {
        User user = users_table.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();
        try {
            String deleteUser = "DELETE FROM user_account WHERE account_id = " + user.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this user ?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(deleteUser);
                showUserList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewRecordsSwitchForm(ActionEvent event) {
        if(event.getSource() == allRecords_button) {
            checkViewRecords = AllRecords;
            viewRecordList = getViewRecords();
            allRecords_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
            defaulterList_button.setStyle("-fx-background-color:transparent");
        } else if(event.getSource() == defaulterList_button) {
            checkViewRecords = DefaulterList;
            viewRecordList = getDefaulterList();
            allRecords_button.setStyle("-fx-background-color:transparent");
            defaulterList_button.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b, #9a2d3d);");
        }
        tableView_viewRecords.setItems(viewRecordList);
    }


    public ObservableList<BorrowTransaction> getViewRecords() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();


        Database connectNow = new Database();
        connect = connectNow.getConnection();
        String sql = "SELECT bt.borrowtransaction_id, d.title AS document_name, \n" +
                    "CONCAT(ua.firstname, ' ', ua.lastname) AS user_name, bt.borrow_date, bt.due_date, bt.status, bt.return_date \n" +
                    "FROM borrowtransaction bt \n" +
                    "JOIN document d ON bt.document_id = d.document_id \n" +
                    "JOIN user_account ua ON bt.account_id = ua.account_id ";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("user_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status"),
                        result.getDate("return_date")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public ObservableList<BorrowTransaction> getDefaulterList() {
        ObservableList<BorrowTransaction> transactions = FXCollections.observableArrayList();


        Database connectNow = new Database();
        connect = connectNow.getConnection();
        String sql ="SELECT bt.borrowtransaction_id, d.title AS document_name, \n" +
                "CONCAT(ua.firstname, ' ', ua.lastname) AS user_name, bt.borrow_date, bt.due_date, bt.status, bt.return_date \n" +
                "FROM borrowtransaction bt \n" +
                "JOIN document d ON bt.document_id = d.document_id \n" +
                "JOIN user_account ua ON bt.account_id = ua.account_id "
                + "WHERE (bt.due_date < CURRENT_DATE AND bt.status = 'borrowing') OR bt.return_date > bt.due_date;";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            BorrowTransaction borrowTransaction;
            while (result.next()) {
                borrowTransaction = new BorrowTransaction(
                        result.getInt("borrowtransaction_id"),
                        result.getString("document_name"),
                        result.getString("user_name"),
                        result.getDate("borrow_date"),
                        result.getDate("due_date"),
                        result.getString("status"),
                        result.getDate("return_date")
                );
                transactions.add(borrowTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void deleteButton_viewRecords() {
        BorrowTransaction transaction = tableView_viewRecords.getSelectionModel().getSelectedItem();
        if (transaction == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Transaction Selected");
            alert.setContentText("Please select a transaction to delete.");
            alert.showAndWait();
            return;
        }
        Database connectNow = new Database();
        Connection connect = connectNow.getConnection();

        try {
            String checkStatusQuery = "SELECT status, document_id FROM borrowtransaction WHERE borrowtransaction_id = ?";
            PreparedStatement checkStatusStmt = connect.prepareStatement(checkStatusQuery);
            checkStatusStmt.setInt(1, transaction.getTransactionId());
            ResultSet resultSet = checkStatusStmt.executeQuery();

            String status = "";
            int documentId = -1;

            if (resultSet.next()) {
                status = resultSet.getString("status");
                documentId = resultSet.getInt("document_id");
            }

            String deleteTransaction = "DELETE FROM borrowtransaction WHERE borrowtransaction_id = '" + transaction.getTransactionId() + "'";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Comfirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this transaction ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                if ("Borrowing".equalsIgnoreCase(status) || "Pending".equalsIgnoreCase(status)) {
                    String updateDocumentQuantity = "UPDATE document SET quantity = quantity + 1 WHERE document_id = ?";
                    PreparedStatement updateStmt = connect.prepareStatement(updateDocumentQuantity);
                    updateStmt.setInt(1, documentId);
                    updateStmt.executeUpdate();
                }

                statement = connect.createStatement();
                statement.executeUpdate(deleteTransaction);
                if(checkViewRecords == AllRecords) {
                    viewRecordList = getViewRecords();
                } else if(checkViewRecords == DefaulterList) {
                    viewRecordList = getDefaulterList();
                }
                tableView_viewRecords.setItems(viewRecordList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBorrowTransactionList() {
        idColumn_viewRecords.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        borrowerName_viewRecords.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        documentName_viewRecords.setCellValueFactory(new PropertyValueFactory<>("documentName"));
        borrowDate_ViewRecords.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDate_viewRecords.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status_viewRecords.setCellValueFactory(new PropertyValueFactory<>("status"));
        returnDate_viewRecords.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        idColumn_viewRecords.setResizable(false);
        borrowerName_viewRecords.setResizable(false);
        documentName_viewRecords.setResizable(false);
        borrowDate_ViewRecords.setResizable(false);
        dueDate_viewRecords.setResizable(false);
        status_viewRecords.setResizable(false);
        returnDate_viewRecords.setResizable(false);
    }

    public void showPieChart() {
        int onTime = 0;
        int late = 0;

        String sql = "SELECT \n" +
                "    SUM(CASE WHEN due_date >= return_date THEN 1 ELSE 0 END) AS on_time,\n" +
                "    SUM(CASE WHEN due_date < return_date OR (status = 'Borrowing' AND due_date < CURDATE()) THEN 1 ELSE 0 END) AS late\n" +
                "FROM borrowtransaction\n" +
                "WHERE status IN ('Returned', 'Borrowing');\n";

        Database connectNow = new Database();
        Connection connection = connectNow.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                onTime = resultSet.getInt("on_time");
                late = resultSet.getInt("late");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Returned On Time", onTime),
                new PieChart.Data("Returned Late", late)
        );
        home_pieChart.setData(pieData);
    }

    public void showBarChart() {
         Database connectNow = new Database();
         Connection connection = connectNow.getConnection();
         String sql = "SELECT d.genre, COUNT(bt.borrowtransaction_id) AS borrow_count\n" +
                 "FROM borrowtransaction bt\n" +
                 "JOIN document d ON bt.document_id = d.document_id\n" +
                 "GROUP BY d.genre;";
         try {
             Statement statement = connection.createStatement();
             ResultSet resultSet =  statement.executeQuery(sql);

             XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
             dataSeries.setName("Borrowed book");

             while(resultSet.next()) {
                 String genre = resultSet.getString("genre");
                 int borrowCount = resultSet.getInt("borrow_count");

                 dataSeries.getData().add(new XYChart.Data<>(genre, borrowCount));
             }
             barChart.getData().add(dataSeries);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    @FXML
    public void initialize (URL Location, ResourceBundle resources) {
        showListDocument();
        showRequestList();
        showUserList();
        availableDocuments_home();
        issuedDocuments_home();
        totalUser_home();
        manageDocumentSelected();
        showBorrowTransactionList();
        searchDocument();
        searchUser();
        checkViewRecords = AllRecords;
        showPieChart();
        showBarChart();
    }

}
