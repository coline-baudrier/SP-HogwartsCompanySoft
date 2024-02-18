package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
    }

    private void loadDataIntoTable() {
        List<Worksite> worksiteList = worksiteDAO.getAllWorksites(); //Liste des sites de travail depuis l'API
        ObservableList<Worksite> worksiteObservableList = FXCollections.observableArrayList(worksiteList); //Crée la liste observable à partir de la liste pour la liaison
        worksiteTable.setItems(worksiteObservableList);
    }

    @FXML
    void addWorksite(ActionEvent event) {

    }

}
