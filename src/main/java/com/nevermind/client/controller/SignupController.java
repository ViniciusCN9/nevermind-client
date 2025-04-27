package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import com.nevermind.client.model.SignupRequest;
import com.nevermind.client.model.SignupResponse;
import com.nevermind.client.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final AuthService authService;

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
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String email = emailInput.getText();

        try {
            SignupRequest request = new SignupRequest(username, password, email);
            SignupResponse response = authService.signup(request);
            SceneManager.showLogin();
        } catch (Exception e) {
            cleanFields();
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    private void cleanFields() {
        usernameInput.clear();
        passwordInput.clear();
        emailInput.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}