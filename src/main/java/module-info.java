module com.nevermind.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nevermind.client to javafx.fxml;
    exports com.nevermind.client;
}