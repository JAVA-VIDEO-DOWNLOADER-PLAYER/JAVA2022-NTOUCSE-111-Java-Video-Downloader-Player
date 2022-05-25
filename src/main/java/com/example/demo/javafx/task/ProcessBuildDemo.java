package com.example.demo.javafx.task;

import java.io.*;

public class ProcessBuildDemo {
    public static void VideoDownloder(String InputURL, String InputPath) throws IOException {

        // TODO 擷取輸入01 - Video URL
//        String str1 = "https://www.youtube.com/watch?v=fukZV1OXv9I";
//        String str1 = "https://twitter.com/i/status/1526213151934787585";

        boolean YoutubeOrTwitter = !InputURL.contains("https://twitter.com"); // TODO true->Youtube; false->Twitter

        // TODO 擷取輸入02 - 存儲檔案路徑 Path
        String str2 = (InputPath.equals("")) ? (GetPath.getPath()) : InputPath;
        // TODO 要使用的終端指令
        System.out.println(str2);
        String commandPrefix = (OSVaildator.isWindows()) ? ("python") : ("python3");
        InputURL = "\""+InputURL+"\"";
        String[] command = {commandPrefix, (YoutubeOrTwitter)?("RunToYoutube.py"):("twitter2mp4.py"), InputURL, "-d", str2};

        if (!runCommand(command)){
            System.out.println("程式錯誤，程式已廢棄");
            return;
        }
        System.out.println("恭喜你成功了");

    }

    private static boolean runCommand(String[] command) throws IOException { // 下載影片
        // TODO ProcessBuilder :
        //  使用指定的操作系統程序和參數構造一個進程構建器。
        //  這是一個方便的構造函數，
        //  它將進程構建器的命令設置為一個字符串列表，
        //  其中包含與命令數組相同的字符串，順序相同。
        //  不檢查命令是否對應於有效的操作系統命令。
        ProcessBuilder probuilder = new ProcessBuilder( command );

        // TODO 影片下載路徑檢查
        if (command[4].equals("")) {
            command[4] = GetPath.getPath();
        }
        // TODO 設置 probuilder 的 工作(即執行command)目錄
        String projectPath = GetPath.getPath("python");
        System.out.println(projectPath);
        probuilder.directory(new File(projectPath));
        // TODO 執行 command
        Process process = probuilder.start();
        return runCommandOutput(process);

    }

    public static void runCommand() throws IOException { // 自動安裝python pkg.
        String pythonFolderPath = GetPath.getPath("python");
        String commandPrefix = (OSVaildator.isWindows()) ? ("pip") : ("pip3");
        //pip install -r /pythonFolderPath/requirements.txt
        String[] command = {commandPrefix, "install", "-r", pythonFolderPath+"requirements.txt"};
        ProcessBuilder probuilder = new ProcessBuilder( command );
        Process process = probuilder.start();

    }

    private static boolean runCommandOutput(Process process){
                //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
}
