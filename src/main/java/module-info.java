module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
//    requires commons.io;
    requires com.opencsv;
    requires commons.beanutils;
    requires java.sql;
//    requires opencsv;

    exports com.example.demo;
    exports com.example.demo.javafx.task;
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.javafx.task to javafx.fxml;
    exports com.example.demo.javafx.task.methods;
    opens com.example.demo.javafx.task.methods to javafx.fxml;
    exports com.example.demo.javafx.task.model;
    opens com.example.demo.javafx.task.model to javafx.fxml;

}