module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.demo to javafx.fxml, com.google.gson;
    exports com.example.demo;
}