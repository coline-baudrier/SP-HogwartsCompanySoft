package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.model.ServiceSite;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Provider;
import java.sql.Timestamp;
import java.util.List;

public class ServiceSiteController {

    @FXML
    private TableColumn<ServiceSite, Integer> addressServiceSite;

    @FXML
    private Button buttonAddServiceSite;

    @FXML
    private TableColumn<ServiceSite, Timestamp> dateCreationServiceSite;

    @FXML
    private TableColumn<ServiceSite, String> emailServiceSite;

    @FXML
    private TableColumn<ServiceSite, Integer> idServiceSite;

    @FXML
    private AnchorPane listServiceSite;

    @FXML
    private AnchorPane menuServiceSite;

    @FXML
    private TableColumn<ServiceSite, String> nameServiceSite;

    @FXML
    private TableColumn<ServiceSite, String> phoneServiceSite;

    @FXML
    private TableView<ServiceSite> serviceSiteTable;

    @FXML
    private TableColumn<ServiceSite, String> typeServiceSite;

    @FXML
    private TableColumn<ServiceSite, Integer> worksiteServiceSite;
    private final ServiceDAO serviceDAO = new ServiceDAO();

    @FXML
    private void initialize() {
        idServiceSite.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nameServiceSite.setCellValueFactory(cellData -> cellData.getValue().nameServiceProperty());
        typeServiceSite.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        phoneServiceSite.setCellValueFactory(cellData -> cellData.getValue().phoneServiceProperty());
        emailServiceSite.setCellValueFactory(cellData -> cellData.getValue().emailServiceProperty());
        dateCreationServiceSite.setCellValueFactory(cellData -> cellData.getValue().dateCreationServiceProperty());
        addressServiceSite.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());
        worksiteServiceSite.setCellValueFactory(cellData -> cellData.getValue().idWorksiteProperty().asObject());

        loadDataIntoTable();

        serviceSiteTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openDetails();
            }
        });
    }

    private void loadDataIntoTable() {
        List<ServiceSite> serviceSiteList = serviceDAO.getAllServiceSites();
        ObservableList<ServiceSite> serviceSitesObservableList = FXCollections.observableArrayList(serviceSiteList);
        serviceSiteTable.setItems(serviceSitesObservableList);
    }
    @FXML
    void addServiceSite(ActionEvent event) {

    }

    @FXML
    void openDetails() {
        ServiceSite selectedServiceSite = serviceSiteTable.getSelectionModel().getSelectedItem();

        if (selectedServiceSite != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsService.fxml"));
                Parent root = loader.load();

                DetailsServiceSiteController controller = loader.getController();

                controller.initData(selectedServiceSite);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Détails du Worksite");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune ligne sélectionnée !");
            alert.showAndWait();
        }
    }

}
