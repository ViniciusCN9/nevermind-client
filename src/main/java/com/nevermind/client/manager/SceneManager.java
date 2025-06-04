package com.nevermind.client.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

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

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource("/com/nevermind/client/css/style.css")).toExternalForm());

            primaryStage.setTitle("Nevermind - End-to-end encrypted chat");
            primaryStage.getIcons().add(
                    new javafx.scene.image.Image(Objects.requireNonNull(
                            SceneManager.class.getResource("/com/nevermind/client/assets/onlylogo.png")
                    ).toExternalForm())
            );
            primaryStage.setScene(scene);
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

    public static void showMain() {
        switchScene("main.fxml");
    }
}