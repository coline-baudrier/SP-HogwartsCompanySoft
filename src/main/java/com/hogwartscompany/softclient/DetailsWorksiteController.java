package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.AddressDAO;
import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Address;
import com.hogwartscompany.softclient.model.Worksite;
import com.hogwartscompany.softclient.model.NewWorksite;
import com.hogwartscompany.softclient.model.UserSession;
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

import java.io.IOException;
import javafx.util.Duration;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DetailsWorksiteController {

    @FXML
    private TextField addressWorksite;

    @FXML
    private TextField buildingFloor;

    @FXML
    private TextField buildingName;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private Button buttonDeleteWorksite;

    @FXML
    private Button buttonUpdateWorksite;

    @FXML
    private TextField cityName;

    @FXML
    private TextField departmentCode;

    @FXML
    private TextField emailWorksite;

    @FXML
    private TextField idWorksite;

    @FXML
    private TextField lineAddress1;

    @FXML
    private TextField lineAddress2;

    @FXML
    private TextField nameWorksite;

    @FXML
    private TextField phoneWorksite;

    @FXML
    private TextField typeWorksite;

    private Worksite selectedWorksite;

    private final WorksiteDAO worksiteDAO = new WorksiteDAO();
    private final AddressDAO addressDAO = new AddressDAO();

    @FXML
    void closePopUp(ActionEvent event) {
        Stage popupStage = (Stage) buttonClosePopUp.getScene().getWindow();
        popupStage.close();

        Stage parentStage = (Stage) popupStage.getOwner();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> reloadWorksitePage(parentStage)));
        timeline.play();
    }

    private void reloadWorksitePage(Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("worksite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            parentStage.setScene(scene);
            parentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteWorksite(ActionEvent event) throws IOException {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
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
        } else {
            buttonDeleteWorksite.setDisable(true);
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
    void updateWorksite(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            idWorksite.setEditable(true);
            nameWorksite.setEditable(true);
            typeWorksite.setEditable(true);
            phoneWorksite.setEditable(true);
            emailWorksite.setEditable(true);
            addressWorksite.setEditable(true);

            Button saveButton = new Button("Enregistrer");
            saveButton.setOnAction(e -> {
                NewWorksite newWorksite = new NewWorksite(
                        nameWorksite.getText(),
                        typeWorksite.getText(),
                        phoneWorksite.getText(),
                        emailWorksite.getText(),
                        Integer.parseInt(addressWorksite.getText())
                );

                int worksiteId = Integer.parseInt(idWorksite.getText());

                try {
                    // Appeler la méthode updateWorksite de votre API avec les nouvelles données
                    worksiteDAO.updateWorksite(worksiteId, newWorksite);
                    // Gérer la réussite de la mise à jour, par exemple, afficher un message de confirmation
                    System.out.println("Site de travail mis à jour avec succès !");
                } catch (IOException ex) {
                    // Gérer les erreurs lors de la mise à jour, par exemple, afficher un message d'erreur
                    ex.printStackTrace();
                    System.err.println("Erreur lors de la mise à jour du site de travail : " + ex.getMessage());
                }
            });

            AnchorPane container = (AnchorPane) idWorksite.getParent();
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

    public void initData(Worksite worksite) {
        selectedWorksite = worksite;
        int addressId = worksite.getIdAddress(); // Obtenir l'ID de l'adresse

        idWorksite.setText(String.valueOf(worksite.getIdWorksite()));
        nameWorksite.setText(worksite.getNameWorksite());
        typeWorksite.setText(worksite.getTypeWorksite());
        phoneWorksite.setText(worksite.getPhoneWorksite());
        emailWorksite.setText(worksite.getEmailWorksite());

        // Charger les détails de l'adresse à partir de son ID
        Address address = addressDAO.getAddressById(addressId);

        if (address != null) {
            addressWorksite.setText(String.valueOf(address.getIdAddress()));
            buildingFloor.setText(String.valueOf(address.getBuildingFloor()));
            buildingName.setText(address.getBuildingName());
            lineAddress1.setText(address.getLineAddress1());
            lineAddress2.setText(address.getLineAddress2());
            departmentCode.setText(String.valueOf(address.getDepartmentCode()));
            cityName.setText(address.getCityName());
        } else {
            // Gérer le cas où l'adresse n'est pas trouvée
        }

        idWorksite.setEditable(false);
        nameWorksite.setEditable(false);
        typeWorksite.setEditable(false);
        phoneWorksite.setEditable(false);
        emailWorksite.setEditable(false);
        addressWorksite.setEditable(false);
        buildingFloor.setEditable(false);
        buildingName.setEditable(false);
        lineAddress1.setEditable(false);
        lineAddress2.setEditable(false);
        departmentCode.setEditable(false);
        cityName.setEditable(false);
    }

}
