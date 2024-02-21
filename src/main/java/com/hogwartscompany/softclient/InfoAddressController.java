package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.AddressDAO;
import com.hogwartscompany.softclient.model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class InfoAddressController {

    @FXML
    private TableView<Address> addressTable;

    @FXML
    private TableColumn<Address, Integer> buildingFloor;

    @FXML
    private TableColumn<Address, String> buildingName;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private TableColumn<Address, String> cityName;

    @FXML
    private TableColumn<Address, Integer> departmentCode;

    @FXML
    private TableColumn<Address, Integer> idAddress;

    @FXML
    private TableColumn<Address, String> lineAddress1;

    @FXML
    private TableColumn<Address, String> lineAddress2;

    private final AddressDAO addressDAO = new AddressDAO();

    @FXML
    void closePopUp(ActionEvent event) {
        Stage popupStage = (Stage) buttonClosePopUp.getScene().getWindow();
        popupStage.close();
    }

    @FXML
    public void initializeAddressTable() {
        idAddress.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());
        buildingFloor.setCellValueFactory(cellData -> cellData.getValue().buildingFloorProperty().asObject());
        buildingName.setCellValueFactory(cellData -> cellData.getValue().buildingNameProperty());
        cityName.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
        departmentCode.setCellValueFactory(cellData -> cellData.getValue().departmentCodeProperty().asObject());
        lineAddress1.setCellValueFactory(cellData -> cellData.getValue().lineAddress1Property());
        lineAddress2.setCellValueFactory(cellData -> cellData.getValue().lineAddress2Property());

        loadDataIntoTable();
    }

    private void loadDataIntoTable() {
        List<Address> addressList = addressDAO.getAllAddress(); //Liste des sites de travail depuis l'API
        ObservableList<Address> addressesObservableList = FXCollections.observableArrayList(addressList); //Crée la liste observable à partir de la liste pour la liaison
        addressTable.setItems(addressesObservableList);
    }

}
