package com.nevermind.client.controller;

import com.nevermind.client.manager.SceneManager;
import com.nevermind.client.service.AuthService;
import com.nevermind.client.service.ChatService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController extends BaseController {

    private final AuthService authService;
    private final ChatService chatService;

    @FXML private MenuItem aboutMenuItem;
    @FXML private MenuItem logoutMenuItem;

    @FXML
    private void initialize() {
        chatService.setOnMessageReceived(this::handleIncomingMessage);
        aboutMenuItem.setOnAction(event -> onAbout());
        logoutMenuItem.setOnAction(event -> onLogout());

        try {
            chatService.connect();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void onAbout() {
        //TODO
    }

    private void onLogout() {
        authService.logout();
        chatService.disconnect();
        SceneManager.showLogin();
    }

    private void handleIncomingMessage(String message) {
        showAlert(message);
    }
}