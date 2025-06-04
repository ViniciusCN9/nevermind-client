package com.nevermind.client.controller;

import com.nevermind.client.config.ClientConfiguration;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class PanelController extends BaseController {

    @FXML private VBox messagesVBox;
    @FXML private ScrollPane chatScrollPane;

    public void addMessage(String from, String content) {
        boolean isOwnMessage = from.equalsIgnoreCase(ClientConfiguration.getCurrentUser().getUsername());
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        Label timestampLabel = new Label(timestamp);
        timestampLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: gray;");
        HBox timestampBox = new HBox(timestampLabel);
        timestampBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        HBox bubbleBox = getBox(content, isOwnMessage);

        VBox messageBox = new VBox(timestampBox, bubbleBox);
        messageBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        messageBox.setPadding(new Insets(5, 10, 5, 10));

        messagesVBox.getChildren().add(messageBox);
    }

    private static HBox getBox(String content, boolean isOwnMessage) {
        Label messageLabel = new Label(content);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);
        messageLabel.setStyle("-fx-padding: 8;" + (isOwnMessage ? "-fx-text-fill: #0e0e0e" : "-fx-text-fill: #ffffff"));

        TextFlow bubble = new TextFlow(messageLabel);
        if (isOwnMessage) {
            bubble.setStyle("-fx-background-color: #57ff5d;-fx-padding: 8 12 8 12;-fx-background-radius: 10;");
        } else {
            bubble.setStyle("-fx-background-color: #353535;-fx-padding: 8 12 8 12;-fx-background-radius: 10;");
        }

        HBox bubbleBox = new HBox(bubble);
        bubbleBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        return bubbleBox;
    }

    public Node getView() {
        return chatScrollPane;
    }
}
