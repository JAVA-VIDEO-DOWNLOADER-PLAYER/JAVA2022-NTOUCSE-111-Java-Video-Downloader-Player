package com.example.demo;
import com.example.demo.javafx.task.DownloaderTask;
import com.example.demo.javafx.task.methods.GetPath;
import com.example.demo.javafx.task.methods.OSVaildator;
import com.example.demo.javafx.task.model.Video;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import static com.example.demo.javafx.task.DownloaderTask.runCommandOutput;
import static java.lang.Thread.sleep;

public class Controller implements Initializable  {
    private double xOffset = 0;
    private double yOffset = 0;
    public Button playbtn;
    public Slider timeSlider;
    public Button backwardbtn;
    public Button forwardbtn;
    public Slider volumeSlider;
    public Label volumeLabel;
    public ImageView closeIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/close.png")));
    public Button minusbtn;
    public Button resizebtn;
    public Button closebtn;
    public ImageView resizeIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/maximize.png")));
    public ImageView minusIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/minus-button.png")));
    public HBox hbox_t;
    ImageView volumeIcon =  new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/volume.png")));
    ImageView volumeMuteIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/mute.png")));

    ImageView stopIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/stop.png")));
    ImageView pauseIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/pause.png")));

    ImageView playIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/play.png")));

    ImageView nextIcon = new  ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/fast-forward.png")));

    ImageView preIcon = new  ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/rewind.png")));

    ImageView minIcon = new ImageView(new Image(new FileInputStream("src/main/java/com/example/demo/icons/minimize.png")));
    public Pane mediaViewPane;
    public AnchorPane Anchor;
    @FXML
    public MediaView backview;
    public StackPane stackpane;
    public Button abort;
    public Button volume;
    MediaPlayer player;
    MediaPlayer backgroundplayer;
    @FXML
    public MediaView mediaview;
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


    static double volumeVal=15.0d;
    public Controller() throws FileNotFoundException {
    }

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
    static boolean max = false;
    static double winH;
    static double winW;
    static boolean alertwindows = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minIcon.setFitWidth(25);
        minIcon.setFitHeight(25);
        minusIcon.setFitWidth(25);
        minusIcon.setFitHeight(25);
        resizeIcon.setFitWidth(25);
        resizeIcon.setFitHeight(25);
        closeIcon.setFitWidth(25);
        closeIcon.setFitHeight(25);
        stopIcon.setFitWidth(30);
        stopIcon.setFitHeight(30);
        playIcon.setFitWidth(30);
        playIcon.setFitHeight(30);
        pauseIcon.setFitHeight(30);
        pauseIcon.setFitWidth(30);
        nextIcon.setFitWidth(30);
        nextIcon.setFitHeight(30);
        preIcon.setFitHeight(30);
        preIcon.setFitWidth(30);
        volumeIcon.setFitHeight(30);
        volumeIcon.setFitWidth(30);
        volumeMuteIcon.setFitHeight(30);
        volumeMuteIcon.setFitWidth(30);
        playbtn.setGraphic(playIcon);
        forwardbtn.setGraphic(nextIcon);
        backwardbtn.setGraphic(preIcon);
        volume.setGraphic(volumeIcon);
        abort.setGraphic(stopIcon);
        minusbtn.setGraphic(minusIcon);
        closebtn.setGraphic(closeIcon);
        resizebtn.setGraphic(resizeIcon);
        video.setCellValueFactory(cellData->cellData.getValue().videoProperty());
        time.setCellValueFactory(cellData->cellData.getValue().timeProperty());
        path.setCellValueFactory(cellData->cellData.getValue().pathProperty());
        vBox.prefHeightProperty().bind(Anchor.heightProperty());
        vBox.prefWidthProperty().bind(Anchor.widthProperty());
        stackpane.prefHeightProperty().bind(Anchor.heightProperty());
        stackpane.prefWidthProperty().bind(Anchor.widthProperty());
        backview.fitHeightProperty().bind(stackpane.heightProperty());
        backview.fitWidthProperty().bind(stackpane.widthProperty());
        borderPane.prefWidthProperty().bind(vBox.widthProperty());//寬度繫結為Pane寬度
        borderPane.prefHeightProperty().bind(vBox.heightProperty());
        scroll.prefHeightProperty().bind(borderPane.heightProperty());
        Thread backgroundVideoTask = new Thread(()->{
            mediaViewPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            Media background = new Media(new File("src/main/java/com/example/demo/background/backgroundView.mp4").toURI().toString());
            backgroundplayer = new MediaPlayer(background);
            backview.setMediaPlayer(backgroundplayer);
            backgroundplayer.setVolume(.05d);
            backgroundplayer.setOnEndOfMedia(()-> backgroundplayer.seek(Duration.ZERO));
            backgroundplayer.play();
        });
        try {
            backgroundVideoTask.start();
            sleep(1300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        volumeSlider.setValue(15);
        volumeLabel.setText(15 +"%");
        volumeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            try{
                volumeVal = volumeSlider.getValue();
                volumeLabel.setText((int) volumeVal +"%");
                player.setVolume(volumeVal * 0.01);
                System.out.println("vol: "+volumeVal);
            }catch (NullPointerException e){
                volumeSlider.setValue(15);
                volumeLabel.setText(15 +"%");
                if (!alertwindows){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alertwindows=!alertwindows;
                    alert.setTitle("Warning");
                    alert.setContentText("你還未選擇影片，無法使用播放器功能!");
                    alert.showAndWait();
                }else{
                    alertwindows = false;
                }
            }
        });
        VideoListView.prefHeightProperty().bind(scroll.heightProperty());
        try {
            initList();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        VideoListView.refresh();
        hbox_t.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        hbox_t.setOnMouseDragged(event -> {
            Stage stage = (Stage) hbox_t.getScene().getWindow();
            stage.setMaximized(false);
            stage.setHeight(600.0);
            stage.setWidth(1000.0);
            winH=600.0;
            winW=1000.0;
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            resizebtn.setGraphic(resizeIcon);
            max = false;
        });
    }
    @FXML
    protected void onSubmitJButtonClick(){
        invokeDownloaderTask();
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
    protected void setVideoTime(Duration time){
        String videoName = player.getMedia().getSource().split("/")[player.getMedia().getSource().split("/").length-1];
        try {
            updateList(videoName.replaceAll(".mp4", ""), time.toSeconds());
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        String funName = "import CsvHandler; CsvHandler.updateCsv('" +videoName.replaceAll(".mp4", "")+ "', " +time.toSeconds()+")";
        String commandPrefix = (OSVaildator.isWindows()) ? ("python") : ("python3");
        String[] command = {commandPrefix, "-c", funName};
        //  執行 command
        Thread runCommamdTask = new Thread(()->{
            try {
                if (runCommand(command)){
                    System.out.println("更新成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        runCommamdTask.setDaemon(true);
        runCommamdTask.start();
    }
    @FXML
    protected void onSelectedItemClick(javafx.scene.input.MouseEvent mouseEvent) throws UnsupportedEncodingException {

        if (mouseEvent.getClickCount()>=2){
            Video video = VideoListView.getSelectionModel().getSelectedItem();
            if (video == null)
                System.out.println("Nothing.");
            else {
                String VideoPath = new File(video.getPath().
                        replaceAll( "\\\\ ",   "\\\\\\\\\\")+"\\"+video.getVideo()+".mp4").toURI().toString();
                VideoPath = new String(VideoPath.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
                System.out.println(VideoPath);
                try {
                    backgroundplayer.setMute(true);
                    Media m = new Media( VideoPath );
                    if (player != null)
                        player.dispose();
                    player = new MediaPlayer(m);
                    mediaview.setMediaPlayer(player);
                    //  要在此處讀取被選取項目之欄位值"當前播放時間"，並載入 player.setStartTime(Duration.seconds(Double Value))
                    player.setOnReady(()->{
                        volumeLabel.setText(15 +"%");
                        timeSlider.setMin(0);
                        timeSlider.setMax(player.getMedia().getDuration().toMinutes());
                        timeSlider.setValue(Double.parseDouble(video.getTime()));
                        player.setStartTime(Duration.seconds(Double.parseDouble(video.getTime())));
                        player.setVolume(volumeSlider.getValue()*0.01);
                        player.setAutoPlay(true);
                        playbtn.setGraphic(pauseIcon);
                    });

                    // listener on player...
                    player.currentTimeProperty().addListener((observableValue, duration, t1) -> {
                        //coding...
                        Duration d= player.getCurrentTime();

                        timeSlider.setValue(d.toMinutes());
                    });
                    // time Slider
                    timeSlider.valueProperty().addListener((observableValue, number, t1) -> {
                        if (timeSlider.isPressed()) {
                            double val = timeSlider.getValue();
                            player.seek(new Duration(val * 60 * 1000));
                        }
                    });

                    player.setOnEndOfMedia(()->{
                        // 將該影片重置
                        setVideoTime(Duration.seconds(0));
                        timeSlider.setValue(Duration.ZERO.toSeconds());
                        volumeLabel.setText(15 +"%");
                        player.dispose();
                        player = null;
                        backgroundplayer.setMute(false);
                        playbtn.setGraphic(playIcon);
                    });
                }catch (Exception error){
                    backgroundplayer.setMute(false);
                    error.printStackTrace();
                }
            }
        }
    }
    public void updateList(String videoName, Double lastTime) throws IOException, CsvValidationException {
        FileReader file = new FileReader("src/main/java/com/example/demo/javafx/task/python/test.csv", StandardCharsets.UTF_8);
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        try(CSVReader csvReader = new CSVReaderBuilder(file).withCSVParser(rfc4180Parser).build()){
            String[] values;
            list.clear();
            while (( values = csvReader.readNext() ) != null){
                System.out.println(Arrays.asList(values));
                if (values[0].equals(videoName)) {
                    values[1] = lastTime.toString();
                }
                if (values[0].equals("video")) {
                    continue;
                }
                list.add(new Video(values[0], values[1], values[2]));
            }
        }
        VideoListView.setItems(list);
    }

    @FXML
    public void play(ActionEvent event) {
        try {
            MediaPlayer.Status status = player.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                player.pause();
                playbtn.setGraphic(playIcon);
                setVideoTime(player.getCurrentTime());
            } else {
                player.play();
                playbtn.setGraphic(pauseIcon);
            }
        } catch (NullPointerException error) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("未選擇影片，無法播放!");
            alert.showAndWait();
        }
    }
    public boolean runCommand(String[] command) throws IOException {
        ProcessBuilder probuilder = new ProcessBuilder( command );

        System.out.println(Arrays.toString(command));
        //  設置 probuilder 的 工作(即執行command)目錄
        String projectPath = GetPath.getPath("python");
        System.out.println(projectPath);
        probuilder.directory(new File(projectPath));
        //  執行 command
        Process process = probuilder.start();
        return runCommandOutput(process);

    }
    @FXML
    public void abort(ActionEvent event) {
        try {
            player.dispose();
            backgroundplayer.setMute(false);
            timeSlider.setValue(0);
        }catch (NullPointerException error){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("你還未選擇影片，無法使用播放器功能!");
            alert.showAndWait();
        }
    }
    @FXML
    public void backwardBtnClicked(ActionEvent event) {
        try {
            double d = player.getCurrentTime().toSeconds();
            player.seek(new Duration((d-10) * 1000));

        }catch (NullPointerException error){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("你還未選擇影片，無法使用播放器功能!");
            alert.showAndWait();
        }
    }
    @FXML
    public void forwardBtnClicked(ActionEvent event) {
        try {
            double d = player.getCurrentTime().toSeconds();
            player.seek(new Duration((d+10) * 1000));
        }catch (NullPointerException error){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("你還未選擇影片，無法使用播放器功能!");
            alert.showAndWait();
        }
    }
    @FXML
    public void volumeMute(ActionEvent event) {
        try {
            if (player.getVolume()!=0){
                // Mute...
                volume.setGraphic(volumeMuteIcon);
                // 設置 volume
                player.setVolume(0);
            }else {
                volume.setGraphic(volumeIcon);
                volumeSlider.setValue(volumeVal);
                player.setVolume(volumeVal*0.01);
            }
        }catch (NullPointerException error){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("你還未選擇影片，無法使用播放器功能!");
            alert.showAndWait();
        }
    }

    @FXML
    public void volumeScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0){
            volumeVal-=2;
        }else {
            volumeVal+=2;
        }
        volumeSlider.setValue(volumeVal);
        volumeLabel.setText((int) volumeVal +"%");

    }

    public void windowmin() {
        Stage stage = (Stage) minusbtn.getScene().getWindow();
        stage.setIconified(true);
    }
    public void windowresize() {
        // get a handle to the stage
        Stage stage = (Stage) resizebtn.getScene().getWindow();
        System.out.println(max);
        if(!max){
            winH = stage.getHeight();
            winW = stage.getWidth();
            // do what you have to do
            stage.setMaximized(true);
            resizebtn.setGraphic(minIcon);
            max = !max;
        } else {
            stage.setMaximized(false);
            stage.setHeight(winH);
            stage.setWidth(winW);
            resizebtn.setGraphic(resizeIcon);
            max = false;
        }
    }

    public void windowsclose() {
        // get a handle to the stage
        Stage stage = (Stage) closebtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}