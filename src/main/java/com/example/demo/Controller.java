package com.example.demo;

import com.example.demo.javafx.task.DownloaderTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import static java.lang.Thread.sleep;

public class Controller implements Initializable  {
    @FXML
    protected ListView<ArrayList<String>> List;
    @FXML
    protected ScrollPane scroll;
    @FXML
    protected HBox hbox;
    @FXML
    protected TextField VideoUrlField;
    @FXML
    protected TextField SavePathField;
    @FXML
    protected BorderPane borderPane;

    @FXML
    protected VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.prefWidthProperty().bind(vBox.widthProperty());//寬度繫結為Pane寬度
        borderPane.prefHeightProperty().bind(vBox.heightProperty());
        scroll.prefHeightProperty().bind(borderPane.heightProperty());
        List.prefWidthProperty().bind(scroll.widthProperty());
        List.prefHeightProperty().bind(scroll.heightProperty());


    }


    @FXML
    protected void onSubmitJButtonClick() {
        invokeDownloaderTask();
    }

    private void invokeDownloaderTask(){
        String fetchFromVideoUrlField = VideoUrlField.getText();
        String fetchFromSavePathField = SavePathField.getText();
        System.out.println("HELLO\nVideo URL: "+fetchFromVideoUrlField+"\nPath: "+fetchFromSavePathField);
        DownloaderTask task = new DownloaderTask(fetchFromVideoUrlField, fetchFromSavePathField);

        task.valueProperty().addListener(
                (observableValue, s, t1) -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("下載任務");
                    alert.setContentText(t1);
                    alert.showAndWait();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
        );
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();

    }

    @FXML
    protected void onBroswerJButtonClick(){
        // TODO 實作 JavaFX DirectoryChooser
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) borderPane.getScene().getWindow();

        File file = directoryChooser.showDialog(stage);

        if(file != null){
            System.out.println("Path: "+file.getAbsolutePath());

            SavePathField.setText(file.getAbsolutePath());
        }else
            SavePathField.setText("");
    }


}