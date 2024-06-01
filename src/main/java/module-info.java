module com.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;


    opens com.example.assignment2.controller to javafx.fxml;

    exports com.example.assignment2;
    exports com.example.assignment2.controller;
}
