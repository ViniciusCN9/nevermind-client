package com.nevermind.client;

import com.nevermind.client.manager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppApplication extends Application {

    private AnnotationConfigApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext();
        context.scan("com.nevermind.client");
        context.refresh();
    }

    @Override
    public void start(Stage stage) {
        SceneManager.init(context, stage);
        SceneManager.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}