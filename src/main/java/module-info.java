module com.hogwartscompany.softclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens com.hogwartscompany.softclient to javafx.fxml;
    exports com.hogwartscompany.softclient;
    exports com.hogwartscompany.softclient.model;
}