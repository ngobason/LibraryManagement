module com.example.librarymanagementsystemapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.json;

    opens com.example.librarymanagementsystemapp to javafx.fxml;
    exports com.example.librarymanagementsystemapp;
}