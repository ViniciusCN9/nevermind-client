package com.nevermind.client.controller;

import com.nevermind.client.config.ClientConfiguration;
import com.nevermind.client.manager.SceneManager;
import com.nevermind.client.service.AuthService;
import com.nevermind.client.service.ChatService;
import com.nevermind.client.util.CryptUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController extends BaseController {

    private final AuthService authService;
    private final ChatService chatService;

    private final List<ContactController> contactControllers = new ArrayList<>();
    private final Map<String, PanelController> chatPanels = new HashMap<>();
    private ContactController selectedContact;

    @FXML
    private VBox contactsVBox;
    @FXML
    private StackPane chatStackPane;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem logoutMenuItem;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button sendButton;

    @FXML
    private void initialize() {
        chatService.setOnMessageReceived(this::handleIncomingMessage);
        aboutMenuItem.setOnAction(e -> onAbout());
        logoutMenuItem.setOnAction(e -> onLogout());
        sendButton.setOnAction(e -> sendMessage());
        messageTextField.setOnAction(e -> sendMessage());

        try {
            chatService.connect();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void onAbout() {
        // TODO: Show about info
    }

    private void onLogout() {
        authService.logout();
        chatService.disconnect();
        ClientConfiguration.setCurrentUser(null);
        selectedContact = null;
        updateInputState();
        SceneManager.showLogin();
    }

    private void sendMessage() {
        String text = messageTextField.getText().trim();
        if (selectedContact == null || text.isEmpty()) return;

        if (text.contains("|")) {
            showAlert("Invalid character: '|'");
            return;
        }

        String recipientUsername = selectedContact.getUsername();
        PublicKey recipientPublicKey = selectedContact.getPublicKey();
        String encryptedMessage = CryptUtil.encode(recipientPublicKey, text);
        try {
            chatService.sendMessage("chat|" + recipientUsername + "|" + encryptedMessage);
        } catch (Exception e) {
            showAlert(e.getMessage());
        }

        PanelController panel = chatPanels.get(recipientUsername);
        if (panel != null) panel.addMessage(ClientConfiguration.getCurrentUser().getUsername(), text);

        messageTextField.clear();
    }

    private void handleIncomingMessage(String message) {
        Platform.runLater(() -> {
            if (message.startsWith("users|")) {
                updateUserList(message);
            } else if (message.startsWith("user|")) {
                updateUserStatus(message);
            } else if (message.startsWith("chat|")) {
                String[] parts = message.split("\\|", 3);
                if (parts.length == 3) {
                    String username = parts[1];
                    String content = parts[2];
                    PanelController panel = chatPanels.get(username);
                    if (panel != null) {
                        panel.addMessage(username, CryptUtil.decode(
                                ClientConfiguration.getCurrentUser().getPrivateKey(),
                                content)
                        );
                    }
                }
            }
        });
    }

    private void updateUserList(String message) {
        contactControllers.clear();
        contactsVBox.getChildren().clear();
        chatPanels.clear();
        chatStackPane.getChildren().clear();

        String[] parts = message.split("\\|");
        if (parts.length > 1) {
            String[] users = parts[1].split(",");
            for (String user : users) {
                addContact(user);
            }
        }
    }

    private void updateUserStatus(String message) {
        String[] parts = message.split("\\|");
        if (parts.length != 3) return;

        String userInfo = parts[1];
        String[] info = userInfo.split(":");
        String username = info[0];
        String status = parts[2];

        if ("connected".equals(status)) {
            boolean exists = contactControllers.stream()
                    .anyMatch(c -> c.getUsername().equals(username));
            if (!exists) addContact(userInfo);
        } else if ("disconnected".equals(status)) {
            contactControllers.removeIf(c -> {
                if (c.getUsername().equals(username)) {
                    contactsVBox.getChildren().remove(c.getView());
                    chatStackPane.getChildren().remove(chatPanels.get(username).getView());
                    chatPanels.remove(username);
                    if (selectedContact == c) selectedContact = null;
                    return true;
                }
                return false;
            });
        }
    }

    private void addContact(String userInfo) {
        try {
            String[] info = userInfo.split(":");
            String username = info[0];
            String publicKey = info[1];

            FXMLLoader contactLoader = new FXMLLoader(SceneManager.class.getResource("/com/nevermind/client/view/contact.fxml"));
            Node contactNode = contactLoader.load();
            ContactController contactController = contactLoader.getController();
            contactController.setUsername(username);
            contactController.setPublicKey(CryptUtil.decodePublicKey(publicKey));

            FXMLLoader panelLoader = new FXMLLoader(SceneManager.class.getResource("/com/nevermind/client/view/panel.fxml"));
            panelLoader.load();
            PanelController panelController = panelLoader.getController();

            chatPanels.put(username, panelController);
            chatStackPane.getChildren().add(panelController.getView());
            panelController.getView().setVisible(false);

            contactNode.setOnMouseClicked(e -> {
                if (selectedContact != null) {
                    selectedContact.deselect();
                    chatPanels.get(selectedContact.getUsername()).getView().setVisible(false);
                }
                contactController.select();
                selectedContact = contactController;
                chatPanels.get(username).getView().setVisible(true);
                updateInputState();
            });

            contactControllers.add(contactController);
            contactsVBox.getChildren().add(contactController.getView());
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    private void updateInputState() {
        boolean enabled = selectedContact != null;
        messageTextField.setDisable(!enabled);
        sendButton.setDisable(!enabled);
    }
}