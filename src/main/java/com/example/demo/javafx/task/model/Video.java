package com.example.demo.javafx.task.model;

import com.opencsv.bean.CsvBindByPosition;
import javafx.beans.property.SimpleStringProperty;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Video {
    @CsvBindByPosition(position = 0)
    protected SimpleStringProperty video;
    @CsvBindByPosition(position = 1)
    protected SimpleStringProperty time;
    @CsvBindByPosition(position = 2)
    protected SimpleStringProperty path;

    public Video(String video, String time, String path) {
        this.video = new SimpleStringProperty(video);
        this.time = new SimpleStringProperty(time);
        this.path = new SimpleStringProperty(path);
    }

    // Getter and Setter

    public String getVideo() {
        return video.get();
    }

    public SimpleStringProperty videoProperty() {
        return video;
    }

    public void setVideo(String video) {
        this.video.set(video);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    @Override
    public String toString() {
        // String s2 = new String(s1.getBytes(“ISO-8859-1”),”GBK”)
        // 之後選項名稱轉換要多一點!!才可以捕捉到正確影片
        return ("Video{" +
                "video=" + video.get() +
                ", time=" + time.get() +
                ", path=" + path.get() +
                '}');
    }
}
