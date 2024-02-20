package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DetailsWorksiteController {

    @FXML
    private TextField addressWorksite;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private Button buttonDeleteWorksite;

    @FXML
    private Button buttonUpdateWorksite;

    @FXML
    private TextField emailWorksite;

    @FXML
    private TextField idWorksite;

    @FXML
    private TextField nameWorksite;

    @FXML
    private TextField phoneWorksite;

    @FXML
    private TextField typeWorksite;

    private Worksite selectedWorksite;
    private final WorksiteDAO worksiteDAO = new WorksiteDAO();

    @FXML
    void closePopUp(ActionEvent event) {
        Scene scene = buttonClosePopUp.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    void deleteWorksite(ActionEvent event) throws IOException {
        int idWorksite = selectedWorksite.getIdWorksite();

        //Boîte de dialogue pour confirmartion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sur de vouloir supprimer ce site ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur confirme la suppression, procéder à la suppression
            try {
                worksiteDAO.deleteWorksite(idWorksite);

                // Afficher une alerte de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression réussie");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le chantier a été supprimé avec succès.");
                successAlert.showAndWait();
            } catch (IOException e) {
                // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur lors de la suppression");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la suppression du chantier : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }

    }

    @FXML
    void updateWorksite(ActionEvent event) {
        idWorksite.setEditable(true);
        nameWorksite.setEditable(true);
        typeWorksite.setEditable(true);
        phoneWorksite.setEditable(true);
        emailWorksite.setEditable(true);
        addressWorksite.setEditable(true);

        Button saveButton = new Button("Enregistrer");

        AnchorPane container = (AnchorPane) idWorksite.getParent();
        container.getChildren().add(saveButton);
        saveButton.setStyle("-fx-background-color : #14A1D9;" +
                            "-fx-background-radius : 15px;" +
                            "-fx-text-fill: white;");
        saveButton.setPrefHeight(43);
        saveButton.setPrefWidth(120);
        AnchorPane.setTopAnchor(saveButton, 631.0);
        AnchorPane.setLeftAnchor(saveButton, 340.0);

    }

    public void initData(Worksite worksite) {
        selectedWorksite = worksite;
        idWorksite.setText(String.valueOf(worksite.getIdWorksite()));
        nameWorksite.setText(worksite.getNameWorksite());
        typeWorksite.setText(worksite.getTypeWorksite());
        phoneWorksite.setText(worksite.getPhoneWorksite());
        emailWorksite.setText(worksite.getEmailWorksite());
        addressWorksite.setText(String.valueOf(worksite.getIdAddress()));

        idWorksite.setEditable(false);
        nameWorksite.setEditable(false);
        typeWorksite.setEditable(false);
        phoneWorksite.setEditable(false);
        emailWorksite.setEditable(false);
        addressWorksite.setEditable(false);
    }

}
