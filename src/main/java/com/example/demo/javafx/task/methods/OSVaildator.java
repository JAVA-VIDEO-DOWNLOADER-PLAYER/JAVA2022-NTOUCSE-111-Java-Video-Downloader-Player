package com.example.demo.javafx.task.methods;

public class OSVaildator { // TODO
    private static final String os = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return os.contains("win");
    }


}
