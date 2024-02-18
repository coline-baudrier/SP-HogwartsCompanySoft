package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.beans.Observable;
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
import java.util.List;

public class WorksiteController {
    @FXML
    private AnchorPane listWorksite;

    @FXML
    private AnchorPane menuWorksite;

    @FXML
    private Button buttonAddWorksite;
    @FXML
    private TableView<Worksite> worksiteTable;

    @FXML
    private TableColumn<Worksite, Integer> addressWorksite;

    @FXML
    private TableColumn<Worksite, String> emailWorksite;

    @FXML
    private TableColumn<Worksite, Integer> idWorksite;

    @FXML
    private TableColumn<Worksite, String>  nameWorksite;

    @FXML
    private TableColumn<Worksite, String>  phoneWorksite;

    @FXML
    private TableColumn<Worksite, String>  typeWorksite;

    private final WorksiteDAO worksiteDAO = new WorksiteDAO();

    @FXML
    private void initialize() {
        //Initialisation des colonnes du tableau
        idWorksite.setCellValueFactory(cellData -> cellData.getValue().idWorksiteProperty().asObject());
        nameWorksite.setCellValueFactory(cellData -> cellData.getValue().nameWorksiteProperty());
        typeWorksite.setCellValueFactory(cellData -> cellData.getValue().typeWorksiteProperty());
        phoneWorksite.setCellValueFactory(cellData -> cellData.getValue().phoneWorksiteProperty());
        emailWorksite.setCellValueFactory(cellData -> cellData.getValue().emailWorksiteProperty());
        addressWorksite.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());

        //Charge les données dans la table
        loadDataIntoTable();

        //Ecouteur évènement
        worksiteTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { //Compte 2 clics
                openDetails();
            }
        });
    }

    private void loadDataIntoTable() {
        List<Worksite> worksiteList = worksiteDAO.getAllWorksites(); //Liste des sites de travail depuis l'API
        ObservableList<Worksite> worksiteObservableList = FXCollections.observableArrayList(worksiteList); //Crée la liste observable à partir de la liste pour la liaison
        worksiteTable.setItems(worksiteObservableList);
    }

    @FXML
    void addWorksite(ActionEvent event) {

    }

    @FXML
    void openDetails() {
        // Récupérer l'objet Worksite correspondant à la ligne sélectionnée
        Worksite selectedWorksite = worksiteTable.getSelectionModel().getSelectedItem();

        //On vérifie qu'un élément est sélectionné
        if (selectedWorksite != null) {
            try {
                //On charge le fichier de la nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsWorksite.fxml"));
                Parent root = loader.load();

                //On récupère le controller de la nouvelle fenêtre
                DetailsWorksiteController controller = loader.getController();
                //On envoie les informations du site sélectionné au controller de la nouvelle fenêtre
                controller.initData(selectedWorksite);
                //Création de la nouvelle scene
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
