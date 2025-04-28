package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import com.nevermind.client.model.LoginRequest;
import com.nevermind.client.model.LoginResponse;
import com.nevermind.client.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoginController extends BaseController {

    private final AuthService authService;

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
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            LoginRequest request = new LoginRequest(username, password);
            LoginResponse response = authService.login(request);
            SceneManager.showChat();
        } catch (Exception e) {
            cleanFields();
            showAlert(e.getMessage());
            logError(e);
        }
    }

    private void cleanFields() {
        usernameInput.clear();
        passwordInput.clear();
    }
}