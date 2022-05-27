package com.example.demo;
import com.example.demo.javafx.task.DownloaderTask;
import com.example.demo.javafx.task.model.Video;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


public class Controller implements Initializable  {
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
    private final ObservableList<Video> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Video> VideoListView;
    @FXML
    private TableColumn<Video, String> video;
    @FXML
    private TableColumn<Video, String> time;
    @FXML
    private TableColumn<Video, String> path;

    public void initList() throws IOException, CsvValidationException {
        FileReader file = new FileReader("src/main/java/com/example/demo/javafx/task/python/test.csv", StandardCharsets.UTF_8);
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        try(CSVReader csvReader = new CSVReaderBuilder(file).withCSVParser(rfc4180Parser).build()){
            String[] values;
            list.clear();
            while (( values = csvReader.readNext() ) != null){
                System.out.println(Arrays.asList(values));
                if(values[0].equals("video")){
                    continue;
                }
                list.add(new Video(values[0], values[1], values[2]));
            }
        }
        VideoListView.setItems(list);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        video.setCellValueFactory(cellData->cellData.getValue().videoProperty());
        time.setCellValueFactory(cellData->cellData.getValue().timeProperty());
        path.setCellValueFactory(cellData->cellData.getValue().pathProperty());
        borderPane.prefWidthProperty().bind(vBox.widthProperty());//寬度繫結為Pane寬度
        borderPane.prefHeightProperty().bind(vBox.heightProperty());
        scroll.prefHeightProperty().bind(borderPane.heightProperty());
        VideoListView.prefHeightProperty().bind(scroll.heightProperty());
        try {
            initList();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        VideoListView.refresh();

    }

    @FXML
    protected void onSubmitJButtonClick(){
        invokeDownloaderTask();
    }

    private void invokeDownloaderTask(){
        String fetchFromVideoUrlField = VideoUrlField.getText();
        String fetchFromSavePathField = SavePathField.getText();
        System.out.println("HELLO\nVideo URL: "+fetchFromVideoUrlField+"\nPath: "+fetchFromSavePathField);
        DownloaderTask task = new DownloaderTask(fetchFromVideoUrlField, fetchFromSavePathField);
        task.valueProperty().addListener(
                (observableValue, s, t1) -> {
                    list.clear();
                    try {
                        initList();
                    } catch (IOException | CsvValidationException e) {
                        throw new RuntimeException(e);
                    }
                    VideoListView.refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("下載任務");
                    alert.setContentText(t1);
                    alert.showAndWait();
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