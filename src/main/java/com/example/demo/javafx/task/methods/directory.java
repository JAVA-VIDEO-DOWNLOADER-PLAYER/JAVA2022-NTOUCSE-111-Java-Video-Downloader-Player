package com.example.demo.javafx.task.methods;

public class directory {
    // TODO 預設存儲資料夾之相對路徑

    public static void main(String[] args) {
        System.out.println(getFolderPath());
    }

    // TODO 取得當前系統的存儲資料夾之絕對路徑
    public static String getFolderPath(){
        return GetPath.getPath();
    }
}
