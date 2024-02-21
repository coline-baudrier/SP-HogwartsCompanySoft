package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.NewWorksite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWorksiteController {
    private final WorksiteDAO worksiteDAO;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    public TextField nameField;

    @FXML
    public TextField typeField;

    @FXML
    public TextField phoneField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField idAddressField;

    public AddWorksiteController() {
        this.worksiteDAO = new WorksiteDAO();
    }

    @FXML
    void closePopUp(ActionEvent event) {
        Scene scene = buttonClosePopUp.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void addWorksite(ActionEvent actionEvent) {
        try {
            String nameWorksite = nameField.getText();
            String typeWorksite = typeField.getText();
            String phoneWorksite = phoneField.getText();
            String emailWorksite = emailField.getText();
            int idAddress = Integer.parseInt(idAddressField.getText());

            NewWorksite newWorksite = new NewWorksite(nameWorksite, typeWorksite, phoneWorksite, emailWorksite, idAddress);
            worksiteDAO.createWorksite(newWorksite);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouveau site de travail ajouté avec succès !");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
