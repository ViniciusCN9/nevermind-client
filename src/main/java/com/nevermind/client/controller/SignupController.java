package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML private TextField usernameInput;
    @FXML private TextField emailInput;
    @FXML private PasswordField passwordInput;
    @FXML private Button signupButton;
    @FXML private Hyperlink loginLink;

    @FXML
    private void initialize() {
        signupButton.setOnAction(e -> doSignup());
        loginLink.setOnAction(e -> SceneManager.showLogin());
    }

    private void doSignup() {
        String user = usernameInput.getText();
        String pass = passwordInput.getText();
        String email = emailInput.getText();

        // TODO: call AuthService to register
        SceneManager.showLogin();
    }
}