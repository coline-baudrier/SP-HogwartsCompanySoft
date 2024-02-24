package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.EmployeeDAO;
import com.hogwartscompany.softclient.model.NewEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class AddEmployeeController {

    @FXML
    private TextField adminField;

    @FXML
    private DatePicker birthdateField;

    @FXML
    private Button buttonAddEmployee;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private TextField cellphoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private DatePicker hiringDateField;

    @FXML
    private TextField idEmployee;

    @FXML
    private TextField idServiceField;

    @FXML
    private TextField jobField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    private final EmployeeDAO employeeDAO;

    public AddEmployeeController() {
        this.employeeDAO = new EmployeeDAO();
    }


    @FXML
    void addEmployee(ActionEvent event) {
        try {
            String firstNameEmployee = firstNameField.getText();
            String lastNameEmployee = lastNameField.getText();
            String jobEmployee = jobField.getText();
            int serviceEmployee = Integer.parseInt(idServiceField.getText());
            String phoneEmployee = phoneField.getText();
            String cellphoneEmployee = cellphoneField.getText();
            String emailEmployee = emailField.getText();

            LocalDate selectedDateBirth = birthdateField.getValue();
            java.sql.Date sqlDateBirth = java.sql.Date.valueOf(selectedDateBirth);
            Timestamp birthDate = new Timestamp(sqlDateBirth.getTime());

            LocalDate selectedDateHiring = hiringDateField.getValue();
            java.sql.Date sqlDateHiring = java.sql.Date.valueOf(selectedDateHiring);
            Timestamp hiringDate = new Timestamp(sqlDateHiring.getTime());

            Boolean adminApplication = Boolean.parseBoolean(adminField.getText());

            NewEmployee newEmployee = new NewEmployee(firstNameEmployee,lastNameEmployee, jobEmployee, serviceEmployee, phoneEmployee, cellphoneEmployee, emailEmployee, birthDate, hiringDate, adminApplication);
            employeeDAO.createEmployee(newEmployee);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouveal employé ajouté avec succès !");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout de l'employé : " + e.getMessage());
            errorAlert.showAndWait();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout de l'employé : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    void closePopUp(ActionEvent event) {
        Scene scene = buttonClosePopUp.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

}
