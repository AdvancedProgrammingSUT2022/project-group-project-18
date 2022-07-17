module civilization {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;

    opens controller to javafx.fxml, com.google.gson;
    opens enums to javafx.fxml, com.google.gson;
    opens model to javafx.fxml, com.google.gson;
    opens view to javafx.fxml, com.google.gson;
    opens civilization to javafx.fxml, com.google.gson;
    opens controller.unitactoins to javafx.fxml, com.google.gson;
    opens model.improvements to javafx.fxml, com.google.gson;
    opens model.unit to javafx.fxml, com.google.gson;
    opens model.techs to javafx.fxml, com.google.gson;
    opens model.Resource to javafx.fxml, com.google.gson;
    opens model.graphicModel to javafx.fxml, com.google.gson, javafx.web, javafx.controls,
            org.controlsfx.controls, com.dlsc.formsfx, validatorfx,
            org.kordamp.ikonli.javafx, org.kordamp.bootstrapfx.core,
            eu.hansolo.tilesfx, javafx.media;


    exports model.graphicModel;
    exports civilization;
    exports controller;
    exports enums;
    exports model;
    exports view;
    exports model.trrain;
    exports model.improvements;
    exports model.unit;
    exports model.techs;
    exports model.Resource;
    exports controller.unitactoins;
}