package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.EmployeeDAO;
import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.Employee;
import com.hogwartscompany.softclient.model.ServiceSite;
import com.hogwartscompany.softclient.model.Worksite;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Time;
import java.sql.Timestamp;
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
    private TableView<ServiceSite> serviceSiteTable;

    @FXML
    private TableColumn<ServiceSite, Integer> addressService;

    @FXML
    private TableColumn<ServiceSite, Timestamp> dateService;

    @FXML
    private TableColumn<ServiceSite, String> emailService;

    @FXML
    private TableColumn<ServiceSite, Integer> idService;

    @FXML
    private TableColumn<ServiceSite, String> nameService;

    @FXML
    private TableColumn<ServiceSite, String> phoneService;

    @FXML
    private TableColumn<ServiceSite, String> typeService;

    @FXML
    private TableColumn<ServiceSite, Integer> worksiteService;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Boolean> adminEmployee;

    @FXML
    private TableColumn<Employee, Timestamp> birthdateEmployee;

    @FXML
    private TableColumn<Employee, String> cellphoneEmployee;

    @FXML
    private TableColumn<Employee, String> emailEmployee;

    @FXML
    private TableColumn<Employee, String> firstNameEmployee;

    @FXML
    private TableColumn<Employee, Timestamp> hiringDateEmployee;

    @FXML
    private TableColumn<Employee, Integer> idEmployee;

    @FXML
    private TableColumn<Employee, Integer> idServiceEmployee;

    @FXML
    private TableColumn<Employee, String> jobEmployee;

    @FXML
    private TableColumn<Employee, String> lastNameEmployee;

    @FXML
    private TableColumn<Employee, String> nameServiceEmployee;

    @FXML
    private TableColumn<Employee, String> phoneEmployee;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField searchField;

    private final WorksiteDAO worksiteDAO = new WorksiteDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    public void initialize() {
        idWorksite.setCellValueFactory(cellData -> cellData.getValue().idWorksiteProperty().asObject());
        nameWorksite.setCellValueFactory(cellData -> cellData.getValue().nameWorksiteProperty());
        typeWorksite.setCellValueFactory(cellData -> cellData.getValue().typeWorksiteProperty());
        phoneWorksite.setCellValueFactory(cellData -> cellData.getValue().phoneWorksiteProperty());
        emailWorksite.setCellValueFactory(cellData -> cellData.getValue().emailWorksiteProperty());
        addressWorksite.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());

        idService.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nameService.setCellValueFactory(cellData -> cellData.getValue().nameServiceProperty());
        typeService.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        phoneService.setCellValueFactory(cellData -> cellData.getValue().phoneServiceProperty());
        emailService.setCellValueFactory(cellData -> cellData.getValue().emailServiceProperty());
        dateService.setCellValueFactory(cellData -> cellData.getValue().dateCreationServiceProperty());
        addressService.setCellValueFactory(cellData -> cellData.getValue().idAddressProperty().asObject());
        worksiteService.setCellValueFactory(cellData -> cellData.getValue().idWorksiteProperty().asObject());

        idEmployee.setCellValueFactory(cellData -> cellData.getValue().idEmployeeProperty().asObject());
        firstNameEmployee.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameEmployee.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneEmployee.setCellValueFactory(cellData -> cellData.getValue().phoneEmployeeProperty());
        cellphoneEmployee.setCellValueFactory(cellData -> cellData.getValue().cellphoneEmployeeProperty());
        emailEmployee.setCellValueFactory(cellData -> cellData.getValue().emailEmployeeProperty());
        hiringDateEmployee.setCellValueFactory(cellData -> cellData.getValue().hiringDateProperty());
        birthdateEmployee.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
        jobEmployee.setCellValueFactory(cellData -> cellData.getValue().jobEmployeeProperty());
        idServiceEmployee.setCellValueFactory(cellData -> cellData.getValue().serviceEmployeeProperty().asObject());
        nameServiceEmployee.setCellValueFactory(cellData -> {
            int serviceId = cellData.getValue().getServiceEmployee();
            ServiceSite service = serviceDAO.getServiceSiteById(serviceId);
            if (service != null) {
                System.out.println("Service récupéré : " + service.getNameService());
                return service.nameServiceProperty();
            } else {
                System.out.println("Aucun service trouvé pour l'ID : " + serviceId);
                return new SimpleStringProperty("Service Inconnu");
            }
        });


        buttonSearch.setOnAction(event -> makeAResearch());

        //TODO : mettre l'écouteur d'évènement pour l'affichage des détails
    }


    public void makeAResearch() {
        String searchTerm = searchField.getText();
        List<Worksite> searchResultsWorksite = worksiteDAO.searchWorksiteByName(searchTerm);
        updateTableWorksite(searchResultsWorksite);
        List<ServiceSite> searchResultService = serviceDAO.searchServiceSiteByName(searchTerm);
        updateTableService(searchResultService);
        List<Employee> searchResultEmployee = employeeDAO.searchEmployeeByName(searchTerm);
        updateTableEmployee(searchResultEmployee);
    }

    private void updateTableWorksite(List<Worksite> searchResults) {
        ObservableList<Worksite> worksiteObservableList = FXCollections.observableArrayList(searchResults);
        worksiteTable.setItems(worksiteObservableList);
    }
    private void updateTableService(List<ServiceSite> searchResults) {
        ObservableList<ServiceSite> serviceSiteObservableList = FXCollections.observableArrayList(searchResults);
        serviceSiteTable.setItems(serviceSiteObservableList);
    }
    private void updateTableEmployee(List<Employee> searchResults) {
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(searchResults);
        employeeTable.setItems(employeeObservableList);
    }

}
