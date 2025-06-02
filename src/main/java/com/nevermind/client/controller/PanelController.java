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
        messageLabel.setStyle("-fx-padding: 8;");

        TextFlow bubble = new TextFlow(messageLabel);
        bubble.setStyle("-fx-background-color: " + (isOwnMessage ? "#acf2bd" : "#eeeeee") + ";" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 4;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 2, 0, 1, 1);");

        HBox bubbleBox = new HBox(bubble);
        bubbleBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        return bubbleBox;
    }

    public Node getView() {
        return chatScrollPane;
    }
}
