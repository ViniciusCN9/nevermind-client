package com.nevermind.client.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class SceneManager {
    private static ApplicationContext context;
    private static Stage primaryStage;

    public static void init(ApplicationContext applicationContext, Stage stage) {
        context = applicationContext;
        primaryStage = stage;
    }

    private static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/nevermind/client/view/" + fxmlPath));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            primaryStage.setTitle("Nevermind - End-to-end encrypted chat");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLogin() {
        switchScene("login.fxml");
    }

    public static void showSignup() {
        switchScene("signup.fxml");
    }

    public static void showChat() {
        switchScene("chat.fxml");
    }
}