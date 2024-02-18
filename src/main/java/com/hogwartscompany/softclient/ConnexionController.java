package com.hogwartscompany.softclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private Button buttonGoToHomePage;

    @FXML
    void goToHomePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
