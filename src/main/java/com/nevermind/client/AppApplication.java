package com.nevermind.client;

import com.nevermind.client.manager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.init(stage);
        SceneManager.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}