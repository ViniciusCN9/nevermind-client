package com.nevermind.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ContactController extends BaseController {

    @FXML
    private GridPane root;
    @FXML private Label nameLabel;

    @Getter
    private String username;

    public void initialize() {
        root.setOnMouseEntered(e -> {
            if (!root.getStyle().contains("#ddd")) {
                root.setStyle("-fx-background-color: #ccc; -fx-cursor: hand;");
            }
        });
        root.setOnMouseExited(e -> {
            if (!root.getStyle().contains("#ddd")) {
                root.setStyle("-fx-background-color: transparent; -fx-cursor: default;");
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
        nameLabel.setText(username);
    }

    public GridPane getView() {
        return root;
    }

    public void select() {
        root.setStyle("-fx-background-color: #ddd;");
    }

    public void deselect() {
        root.setStyle("-fx-background-color: transparent;");
    }
}
