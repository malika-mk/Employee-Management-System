module org.example.application {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.application to javafx.fxml;
    exports org.example.application;
}