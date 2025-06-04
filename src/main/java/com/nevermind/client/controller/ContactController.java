package com.nevermind.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.security.PublicKey;

@Controller
@RequiredArgsConstructor
public class ContactController extends BaseController {

    @FXML
    private GridPane root;
    @FXML private Label nameLabel;

    @Getter
    private String username;

    @Getter
    @Setter
    private PublicKey publicKey;

    public void initialize() {
        root.setOnMouseEntered(e -> {
            if (!root.getStyle().contains("#2B2B2B")) {
                root.setStyle("-fx-background-color: #353535; -fx-cursor: hand;");
            }
        });
        root.setOnMouseExited(e -> {
            if (!root.getStyle().contains("#2B2B2B")) {
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
        root.setStyle("-fx-background-color: #2B2B2B;");
    }

    public void deselect() {
        root.setStyle("-fx-background-color: transparent;");
    }
}
