package com.example.demo.javafx.task;

import com.example.demo.javafx.task.model.Video;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.List;

public class CsvTask {

    public void ReadCsv() throws IOException {
        FileReader file = new FileReader("src/main/java/com/example/demo/javafx/task/python/test.csv", StandardCharsets.UTF_8);

        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        final CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(file)
                .withCSVParser(rfc4180Parser);
        List<Video> beans = new CsvToBeanBuilder<Video>(csvReaderBuilder.build())
                .withType(Video.class)
                .build()
                .parse();

//        for (Video video : beans) {
//            System.out.println(video.toString());
//        }
    }
}
