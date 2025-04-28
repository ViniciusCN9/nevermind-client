package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import com.nevermind.client.model.SignupRequest;
import com.nevermind.client.model.SignupResponse;
import com.nevermind.client.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class SignupController extends BaseController {

    private final AuthService authService;

    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField confirmPasswordInput;
    @FXML private Button signupButton;
    @FXML private Hyperlink loginLink;

    @FXML
    private void initialize() {
        signupButton.setOnAction(e -> doSignup());
        loginLink.setOnAction(e -> SceneManager.showLogin());
    }

    private void doSignup() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        if (!Objects.equals(password, confirmPassword)) {
            showAlert("Password does not match");
            return;
        }

        try {
            SignupRequest request = new SignupRequest(username, password);
            SignupResponse response = authService.signup(request);
            SceneManager.showLogin();
        } catch (Exception e) {
            cleanFields();
            showAlert(e.getMessage());
            logError(e);
        }
    }

    private void cleanFields() {
        usernameInput.clear();
        passwordInput.clear();
        confirmPasswordInput.clear();
    }
}