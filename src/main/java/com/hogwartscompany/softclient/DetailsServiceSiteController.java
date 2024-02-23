package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.AddressDAO;
import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class DetailsServiceSiteController {

    @FXML
    private TextField addressServiceSite;

    @FXML
    private TextField worksiteServiceSite;

    @FXML
    private TextField buildingFloor;

    @FXML
    private TextField buildingName;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private Button buttonDeleteService;

    @FXML
    private Button buttonDisplayAddress;

    @FXML
    private Button buttonUpdateWorksite;

    @FXML
    private TextField cityName;

    @FXML
    private TextField dateCreationServiceSite;

    @FXML
    private TextField departmentCode;

    @FXML
    private TextField emailServiceSite;

    @FXML
    private TextField idServiceSite;

    @FXML
    private TextField lineAddress1;

    @FXML
    private TextField lineAddress2;

    @FXML
    private TextField nameServiceSite;

    @FXML
    private TextField nameWorksite;

    @FXML
    private TextField phoneServiceSite;

    @FXML
    private TextField typeServiceSite;

    private ServiceSite selectedServiceSite;

    private WorksiteDAO worksiteDAO = new WorksiteDAO();
    private AddressDAO addressDAO = new AddressDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();

    @FXML
    void closePopUp(ActionEvent event) {
        Stage popupStage = (Stage) buttonClosePopUp.getScene().getWindow();
        popupStage.close();

        Stage parentStage = (Stage) popupStage.getOwner();
    }

    @FXML
    void deleteServiceSite(ActionEvent event) throws IOException {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            int idService = selectedServiceSite.getIdService();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sur de vouloir supprimer ce site ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    serviceDAO.deleteServiceSite(idService);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Le chantier a été supprimé avec succès.");
                    successAlert.showAndWait();
                } catch (IOException e) {

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur lors de la suppression");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Erreur lors de la suppression du chantier : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            buttonDeleteService.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Visiblement tu n'as pas passé tes ASPIC, tu ne peux rien supprimer par ici !");
            alert.showAndWait();
        }

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

    @FXML
    void updateServiceSite(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();

        if (isAdmin) {
            idServiceSite.setEditable(true);
            nameServiceSite.setEditable(true);
            typeServiceSite.setEditable(true);
            phoneServiceSite.setEditable(true);
            emailServiceSite.setEditable(true);
            addressServiceSite.setEditable(true);

            Button saveButton = new Button("Enregistrer");
            saveButton.setOnAction(e -> {
                // Récupération du texte de la cellule de la date
                String dateString = dateCreationServiceSite.getText();

                // Format de la date attendu
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    // Conversion du texte en objet Timestamp
                    java.util.Date parsedDate = dateFormat.parse(dateString);
                    Timestamp timestamp = new Timestamp(parsedDate.getTime());

                    // Création de l'objet NewServiceSite avec la date convertie
                    NewServiceSite newServiceSite = new NewServiceSite(
                            nameServiceSite.getText(),
                            typeServiceSite.getText(),
                            phoneServiceSite.getText(),
                            emailServiceSite.getText(),
                            timestamp,
                            Integer.parseInt(addressServiceSite.getText()),
                            Integer.parseInt(worksiteServiceSite.getText())
                    );

                    int serviceSiteId = Integer.parseInt(idServiceSite.getText());

                    try {
                        serviceDAO.updateServiceSite(serviceSiteId, newServiceSite);
                        System.out.println("Site de travail mis à jour avec succès !");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.err.println("Erreur lors de la mise à jour du site de travail : " + ex.getMessage());
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    System.err.println("Erreur lors de la conversion de la date : " + ex.getMessage());
                }
            });

            AnchorPane container = (AnchorPane) idServiceSite.getParent();
            container.getChildren().add(saveButton);
            saveButton.setStyle("-fx-background-color : #14A1D9;" +
                    "-fx-background-radius : 15px;" +
                    "-fx-text-fill: white;");
            saveButton.setPrefHeight(43);
            saveButton.setPrefWidth(120);
            AnchorPane.setTopAnchor(saveButton, 631.0);
            AnchorPane.setLeftAnchor(saveButton, 340.0);
        } else {
            buttonUpdateWorksite.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Visiblement tu n'as pas passé tes ASPIC, tu ne peux rien modifier par ici !");
            alert.showAndWait();
        }
    }

    public void initData(ServiceSite serviceSite) {
        selectedServiceSite = serviceSite;
        int addressId = serviceSite.getIdAddress();
        int worksiteId = serviceSite.getIdWorksite();

        idServiceSite.setText(String.valueOf(serviceSite.getIdService()));
        nameServiceSite.setText(serviceSite.getNameService());
        typeServiceSite.setText(serviceSite.getTypeService());
        phoneServiceSite.setText(serviceSite.getPhoneService());
        emailServiceSite.setText(serviceSite.getEmailService());
        dateCreationServiceSite.setText(String.valueOf(serviceSite.getDateCreationService()));

        Address address = addressDAO.getAddressById(addressId);

        if (address != null) {
            addressServiceSite.setText(String.valueOf(address.getIdAddress()));
            buildingFloor.setText(String.valueOf(address.getBuildingFloor()));
            buildingName.setText(address.getBuildingName());
            lineAddress1.setText(address.getLineAddress1());
            lineAddress2.setText(address.getLineAddress2());
            departmentCode.setText(String.valueOf(address.getDepartmentCode()));
            cityName.setText(address.getCityName());
        } else {
            // Gérer le cas où l'adresse n'est pas trouvée
        }

        Worksite worksite = worksiteDAO.getWorksiteById(worksiteId);

        if (worksite != null) {
            worksiteServiceSite.setText(String.valueOf(worksite.getIdWorksite()));
            nameWorksite.setText(worksite.getNameWorksite());
        } else {

        }

        idServiceSite.setEditable(false);
        nameServiceSite.setEditable(false);
        typeServiceSite.setEditable(false);
        phoneServiceSite.setEditable(false);
        dateCreationServiceSite.setEditable(false);
        emailServiceSite.setEditable(false);
        addressServiceSite.setEditable(false);
        buildingFloor.setEditable(false);
        buildingName.setEditable(false);
        lineAddress1.setEditable(false);
        lineAddress2.setEditable(false);
        departmentCode.setEditable(false);
        cityName.setEditable(false);
        nameWorksite.setEditable(false);
    }

}
