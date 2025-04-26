package com.nevermind.client.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    private static void switchScene(String fxmlPath, String title, int w, int h) {
        try {
            Parent root = FXMLLoader.load(
                    SceneManager.class.getResource("/com/nevermind/client/view/" + fxmlPath)
            );
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root, w, h));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLogin() {
        switchScene("login.fxml", "Login", 800, 600);
    }

    public static void showSignup() {
        switchScene("signup.fxml", "Sign Up", 800, 600);
    }

    public static void showChat() {
        switchScene("chat.fxml", "Chat Room", 800, 600);
    }
}