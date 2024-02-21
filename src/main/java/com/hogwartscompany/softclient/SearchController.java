package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;


public class SearchController {

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

    @FXML
    private Tab resultsWorksite;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField searchField;

    private final WorksiteDAO worksiteDAO = new WorksiteDAO();

    @FXML
    public void initialize() {
        idWorksite.setCellValueFactory(cellData -> cellData.getValue().idWorksiteProperty().asObject());
        nameWorksite.setCellValueFactory(cellData -> cellData.getValue().nameWorksiteProperty());
        typeWorksite.setCellValueFactory(cellData -> cellData.getValue().typeWorksiteProperty());
        phoneWorksite.setCellValueFactory(cellData -> cellData.getValue().phoneWorksiteProperty());
        emailWorksite.setCellValueFactory(cellData -> cellData.getValue().emailWorksiteProperty());
        addressWorksite.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());

        buttonSearch.setOnAction(event -> makeAResearch());

        //TODO : mettre l'écouteur d'évènement pour l'affichage des détails
    }


    public void makeAResearch() {
        String searchTerm = searchField.getText();
        List<Worksite> searchResults = worksiteDAO.searchWorksiteByName(searchTerm);
        updateTable(searchResults);
    }

    private void updateTable(List<Worksite> searchResults) {
        ObservableList<Worksite> worksiteObservableList = FXCollections.observableArrayList(searchResults);
        worksiteTable.setItems(worksiteObservableList);
    }

}
