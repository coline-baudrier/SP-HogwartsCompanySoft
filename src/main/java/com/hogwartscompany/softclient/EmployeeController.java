package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.EmployeeDAO;
import com.hogwartscompany.softclient.model.Employee;
import com.hogwartscompany.softclient.model.ServiceSite;
import com.hogwartscompany.softclient.model.UserSession;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class EmployeeController {

    @FXML
    private TableColumn<Employee, Timestamp> birthdateEmployee;

    @FXML
    private Button buttonAddEmployee;

    @FXML
    private TableColumn<Employee, String> cellphoneEmployee;

    @FXML
    private TableColumn<Employee, String> emailEmployee;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> firstNameEmployee;

    @FXML
    private TableColumn<Employee, Timestamp> hiringDateEmployee;

    @FXML
    private TableColumn<Employee, Integer> idEmployee;

    @FXML
    private TableColumn<Employee, Integer> isAdminEmployee;

    @FXML
    private TableColumn<Employee, String> jobEmployee;

    @FXML
    private TableColumn<Employee, String> lastNameEmployee;

    @FXML
    private AnchorPane listEmployee;

    @FXML
    private AnchorPane menuEmployee;

    @FXML
    private TableColumn<Employee, String> phoneEmployee;

    @FXML
    private TableColumn<Employee, Integer> serviceEmployee;

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    private void initialize() {
        idEmployee.setCellValueFactory(cellData -> cellData.getValue().idEmployeeProperty().asObject());
        firstNameEmployee.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameEmployee.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        jobEmployee.setCellValueFactory(cellData -> cellData.getValue().jobEmployeeProperty());
        serviceEmployee.setCellValueFactory(cellData -> cellData.getValue().serviceEmployeeProperty().asObject());
        phoneEmployee.setCellValueFactory(cellData -> cellData.getValue().phoneEmployeeProperty());
        cellphoneEmployee.setCellValueFactory(cellData -> cellData.getValue().cellphoneEmployeeProperty());
        emailEmployee.setCellValueFactory(cellData -> cellData.getValue().emailEmployeeProperty());
        birthdateEmployee.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
        hiringDateEmployee.setCellValueFactory(cellData -> cellData.getValue().hiringDateProperty());
        isAdminEmployee.setCellValueFactory(cellData -> cellData.getValue().idEmployeeProperty().asObject());

        loadDataIntoTable();

        employeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openDetails();
            }
        });

    }

    private void loadDataIntoTable() {
        List<Employee> employeeList = employeeDAO.getAllEmployees();
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(employeeList);
        employeeTable.setItems(employeeObservableList);
    }

    @FXML
    void addEmployee(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage modalStage = new Stage();
                modalStage.setScene(scene);
                modalStage.setTitle("Ajouter un employé");

                modalStage.initModality(Modality.APPLICATION_MODAL);

                modalStage.showAndWait();
                loadDataIntoTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            buttonAddEmployee.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Visiblement tu n'as pas passé tes ASPIC, tu ne peux rien ajouter par ici !");
            alert.showAndWait();
            loadDataIntoTable();
        }

    }

    @FXML
    void openDetails() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsEmployee.fxml"));
                Parent root = loader.load();

                DetailsEmployeeController controller = loader.getController();

                controller.initData(selectedEmployee);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Détails employé");
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
            loadDataIntoTable();
        }
    }

}
