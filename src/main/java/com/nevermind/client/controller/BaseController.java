package com.nevermind.client.controller;

import javafx.scene.control.Alert;

public class BaseController {

    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void logError(Exception e) {
        e.printStackTrace();
    }
}
