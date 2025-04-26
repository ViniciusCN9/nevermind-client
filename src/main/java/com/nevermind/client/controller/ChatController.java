package com.nevermind.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML private TextArea chatArea;
    @FXML private TextField messageField;

    @FXML
    private void initialize() {
        // TODO: init WebSocket connection and message listener
    }

    @FXML
    private void sendMessage() {
        String msg = messageField.getText();
        if (!msg.isEmpty()) {
            // TODO: send via WebSocket
            chatArea.appendText("Me: " + msg + "\n");
            messageField.clear();
        }
    }
}