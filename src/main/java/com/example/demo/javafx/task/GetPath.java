package com.example.demo.javafx.task;

public class GetPath {
    private static final String python = "\\src\\main\\java\\com\\example\\demo\\javafx\\task\\python\\";
    private static final String defaultPath = "\\Java-Downloader-Folder\\video\\";  //TODO 預設下載路徑
//    private final String;

    public static String getPath(String filename) {
        if ("python".equals(filename)) {
            if (OSVaildator.isWindows()){
                return System.getProperty("user.dir") + python;
            } else {
                return System.getProperty("user.dir") + python;
            }
        }
        return "";
    }
    public static String getPath() {
        if (OSVaildator.isWindows()){
            return System.getProperty("user.home") + defaultPath.replaceAll("\\\\", "/");
        } else {
            return System.getProperty("user.home") + defaultPath.replaceAll("\\\\", "/");
        }

    }

}
