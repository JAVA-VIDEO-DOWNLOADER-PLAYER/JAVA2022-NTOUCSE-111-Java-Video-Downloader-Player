package com.example.demo.javafx.task.model;

import com.opencsv.bean.CsvBindByPosition;

public class Video {
    @CsvBindByPosition(position = 0)
    protected String video;
    @CsvBindByPosition(position = 1)
    protected String time;
    @CsvBindByPosition(position = 2)
    protected String path;
    // Getter and Setter
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Video{" +
                "video='" + video + '\'' +
                ", time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
