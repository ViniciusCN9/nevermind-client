package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private Button loginButton;
    @FXML private Hyperlink signupLink;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> doLogin());
        signupLink.setOnAction(e -> SceneManager.showSignup());
    }

    private void doLogin() {
        String user = usernameInput.getText();
        String pass = passwordInput.getText();
        // TODO: call AuthService to authenticate
        boolean success = true;
        if (success) {
            SceneManager.showChat();
        } else {
            // show error
        }
    }
}