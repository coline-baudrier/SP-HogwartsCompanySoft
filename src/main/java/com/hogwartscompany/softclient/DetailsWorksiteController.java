package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.AddressDAO;
import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.*;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.util.Duration;

import java.security.Provider;
import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;

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

    @FXML
    private TableColumn<ServiceSite, Integer> idServiceOfWorksite;

    @FXML
    private TableColumn<ServiceSite, String> nameServiceOfWorksite;

    @FXML
    private TableColumn<ServiceSite, String> phoneServiceOfWorksite;

    @FXML
    private TableColumn<ServiceSite, String> typeServiceOfWorksite;

    private Worksite selectedWorksite;

    private final WorksiteDAO worksiteDAO = new WorksiteDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final AddressDAO addressDAO = new AddressDAO();

    @FXML
    private TableView<ServiceSite> listServicesOfWorksite;




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
            confirmationAlert.setTitle("Une merveilleuse idée ?");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sur de vouloir supprimer cette annexe du château ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    worksiteDAO.deleteWorksite(idWorksite);

                    // Afficher une alerte de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("L'annexe du château a été supprimée avec succès.");
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
        //Affichage de la liste d'adresses disponibles
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
        //Update d'un worksite avec la possibilité d'écrire dans certaines cases
        //Récupération des informations tapées et non changées
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
                //Modification avec le passage des paramètres
                NewWorksite newWorksite = new NewWorksite(
                        nameWorksite.getText(),
                        typeWorksite.getText(),
                        phoneWorksite.getText(),
                        emailWorksite.getText(),
                        Integer.parseInt(addressWorksite.getText())
                );

                int worksiteId = Integer.parseInt(idWorksite.getText());

                try {
                    if (!isNumeric(phoneWorksite.getText())) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Immobilis !");
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

            //Ajout d'un bouton enregistrer
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
        //Initialisation de la donnée en allant la récupérer dans l'API et pour l'afficher dans les fields concernés
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

        idWorksite.setEditable(true);
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

    public void initialize() {
        //Affichage des données dans le tableau de détail
        idServiceOfWorksite.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nameServiceOfWorksite.setCellValueFactory(cellData -> cellData.getValue().nameServiceProperty());
        typeServiceOfWorksite.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        phoneServiceOfWorksite.setCellValueFactory(cellData -> cellData.getValue().phoneServiceProperty());
    }

    public void loadDataIntoTableService() {
        //Chargement des données
        int idWorksiteDetails = selectedWorksite.getIdWorksite();
        if (idWorksiteDetails != 0) {
            List<ServiceSite> listServices = serviceDAO.getServicesByWorksite(idWorksiteDetails);
            ObservableList<ServiceSite> servicesObservableList = FXCollections.observableArrayList(listServices);
            listServicesOfWorksite.setItems(servicesObservableList);
        } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("L'id du site n'a pas été trouvé");
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
