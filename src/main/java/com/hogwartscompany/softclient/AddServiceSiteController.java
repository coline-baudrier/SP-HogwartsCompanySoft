package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.model.NewServiceSite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;

import java.sql.Timestamp;

public class AddServiceSiteController {

    private final ServiceDAO serviceDAO;

    @FXML
    private Button buttonAddWorksite;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private TextField emailField;

    @FXML
    private TextField idAddressField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField worksiteField;

    public AddServiceSiteController() {
        this.serviceDAO = new ServiceDAO();
    }

    @FXML
    void addWorksite(ActionEvent event) {
        try {
            String nameServiceSite = nameField.getText();
            String typeServiceSite = typeField.getText();
            String phoneServiceSite = phoneField.getText();
            String emailServiceSite = emailField.getText();

            if (!isNumeric(phoneServiceSite)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Immobilis");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Les moldus n'utilisent que des chiffres dans leurs numéros de téléphone et pas plus de 10 !");
                errorAlert.showAndWait();
                return;
            }

            if (!isValidEmail(String.valueOf(emailServiceSite))) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("L'adresse email n'est pas valide.");
                errorAlert.showAndWait();
                return;
            }


            LocalDate selectedDate = dateField.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
            Timestamp dateCreationServiceSite = new Timestamp(sqlDate.getTime());

            int idAddress = Integer.parseInt(idAddressField.getText());
            int idWorksite = Integer.parseInt(worksiteField.getText());

            NewServiceSite newServiceSite = new NewServiceSite(nameServiceSite, typeServiceSite, phoneServiceSite, emailServiceSite, dateCreationServiceSite, idAddress, idWorksite);
            serviceDAO.createServiceSite(newServiceSite);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Gemino!");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Une nouvelle tour à été ajoutée au château !");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Immobilis !");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Immobilis !");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    void closePopUp(ActionEvent event) {
        Scene scene = buttonClosePopUp.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    void displayAddress(ActionEvent event) {
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
