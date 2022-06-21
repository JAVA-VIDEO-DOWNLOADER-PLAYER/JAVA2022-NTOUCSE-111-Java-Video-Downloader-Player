package com.example.demo;

import com.example.demo.javafx.task.DownloaderTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class JavaMainGuiTest extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        //DownloaderTask.runCommand(); // 更新 python 套件
        FXMLLoader fxmlLoader = new FXMLLoader(JavaMainGuiTest.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600); //
        stage.initStyle(StageStyle.UNDECORATED);
        Controller controller = new Controller();
        fxmlLoader.setController(controller);
        stage.setTitle("Hello!");
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}