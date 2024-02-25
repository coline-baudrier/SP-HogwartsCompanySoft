package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.NewWorksite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
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

    //Permet de fermer la PopUp
    @FXML
    void closePopUp(ActionEvent event) {
        Scene scene = buttonClosePopUp.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void addWorksite(ActionEvent actionEvent) {
        try {
            //Récupération du texte inscrit par l'administrateur
            String nameWorksite = nameField.getText();
            String typeWorksite = typeField.getText();
            String phoneWorksite = phoneField.getText();
            String emailWorksite = emailField.getText();
            int idAddress = Integer.parseInt(idAddressField.getText());

            if (!isNumeric(phoneWorksite)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Immobilis");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Les moldus n'utilisent que des chiffres dans leurs numéros de téléphone et pas plus de 10 !");
                errorAlert.showAndWait();
                return;
            }

            if (!isValidEmail(String.valueOf(emailWorksite))) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("L'adresse email n'est pas valide.");
                errorAlert.showAndWait();
                return;
            }

            //Création de la nouvelle occurence avec le passage des paramètres
            NewWorksite newWorksite = new NewWorksite(nameWorksite, typeWorksite, phoneWorksite, emailWorksite, idAddress);
            worksiteDAO.createWorksite(newWorksite);

            //Fenêtre d'alerte pour le succès de l'ajout
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Gemino");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouvelle annexe au château ajoutée avec succès !");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Immobilis !");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Immobils !");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    void displayAddress(ActionEvent event) {
        //Permet d'afficher la liste des adresses disponibles
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("infoAddress.fxml"));
            Parent root = loader.load();

            InfoAddressController controller = loader.getController();
            controller.initializeAddressTable();

            Scene scene = new Scene(root);

            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setTitle("Liste des adresses");
            modalStage.initModality(Modality.APPLICATION_MODAL);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String stringType) {
        return stringType != null && stringType.matches("\\d+") && stringType.length() == 10;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
