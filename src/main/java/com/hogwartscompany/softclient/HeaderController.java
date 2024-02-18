package com.hogwartscompany.softclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderController {

    @FXML
    private Button buttonDisconnect;

    @FXML
    private Button buttonGoToEmployee;

    @FXML
    private Button buttonGoToService;

    @FXML
    private Button buttonGoToWorksite;

    @FXML
    private Button buttonSearchResults;

    @FXML
    void disconnectAndClose(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonDisconnect.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToEmployee.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToService(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("service.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToService.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToWorksite(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("worksite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToWorksite.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchResults(ActionEvent event) {

    }

}
