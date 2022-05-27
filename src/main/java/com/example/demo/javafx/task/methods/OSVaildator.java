package com.example.demo.javafx.task.methods;

public class OSVaildator { // TODO
    private static final String os = System.getProperty("os.name").toLowerCase();

    protected static boolean isUNIX() {

        return (os.contains("nix") || os.contains("nux"));
    }

    protected static boolean isMac() {
        return os.contains("mac");
    }

    public static boolean isWindows() {
        return os.contains("win");
    }


}
