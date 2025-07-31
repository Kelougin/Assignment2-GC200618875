module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.demo to javafx.fxml, com.google.gson;
    exports com.example.demo;
    exports com.example.demo.Model;
    opens com.example.demo.Model to com.google.gson, javafx.fxml;
    exports com.example.demo.API;
    opens com.example.demo.API to com.google.gson, javafx.fxml;
    exports com.example.demo.Controller;
    opens com.example.demo.Controller to com.google.gson, javafx.fxml;
}