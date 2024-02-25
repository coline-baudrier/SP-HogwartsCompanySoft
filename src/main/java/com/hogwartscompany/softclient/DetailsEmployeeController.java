package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.dao.AddressDAO;
import com.hogwartscompany.softclient.dao.EmployeeDAO;
import com.hogwartscompany.softclient.dao.ServiceDAO;
import com.hogwartscompany.softclient.dao.WorksiteDAO;
import com.hogwartscompany.softclient.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class DetailsEmployeeController {
    @FXML
    private TextField addressEmployee;

    @FXML
    private TextField employeeAdmin;

    @FXML
    private TextField birthdateEmployee;

    @FXML
    private TextField buildingFloor;

    @FXML
    private TextField buildingName;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private Button buttonDeleteEmployee;

    @FXML
    private Button buttonUpdateEmployee;

    @FXML
    private TextField cellphoneEmployee;

    @FXML
    private TextField cityName;

    @FXML
    private TextField departmentCode;

    @FXML
    private TextField emailEmployee;

    @FXML
    private TextField firstNameEmployee;

    @FXML
    private TextField hiringDateEmployee;

    @FXML
    private TextField idEmployee;

    @FXML
    private TextField idServiceSite;

    @FXML
    private TextField idWorksite;

    @FXML
    private TextField jobEmployee;

    @FXML
    private TextField lastNameEmployee;

    @FXML
    private TextField lineAddress1;

    @FXML
    private TextField lineAddress2;

    @FXML
    private TextField nameServiceSite;

    @FXML
    private TextField nameWorksite;

    @FXML
    private TextField phoneEmployee;

    private Employee selectedEmployee;
    private ServiceSite serviceEmployee;
    private WorksiteDAO worksiteDAO = new WorksiteDAO();
    private AddressDAO addressDAO = new AddressDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    void closePopUp(ActionEvent event) {
        Stage popupStage = (Stage) buttonClosePopUp.getScene().getWindow();
        popupStage.close();

    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            int idService = selectedEmployee.getIdEmployee();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Une merveilleuse idée ?");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sur de vouloir supprimer ce sorcier ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    serviceDAO.deleteServiceSite(idService);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Avada Kedavra");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Le sorcier a été supprimé avec succès.");
                    successAlert.showAndWait();
                } catch (IOException e) {

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Immobilis !");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Le sorcier n'a pas été supprimé parce que : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            buttonDeleteEmployee.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Visiblement tu n'as pas passé tes ASPIC, tu ne peux rien supprimer par ici !");
            alert.showAndWait();
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();

        if (isAdmin) {
            firstNameEmployee.setEditable(true);
            lastNameEmployee.setEditable(true);
            jobEmployee.setEditable(true);
            phoneEmployee.setEditable(true);
            cellphoneEmployee.setEditable(true);
            emailEmployee.setEditable(true);
            hiringDateEmployee.setEditable(false);
            birthdateEmployee.setEditable(false);
            employeeAdmin.setEditable(true);
            idServiceSite.setEditable(true);

            Button saveButton = new Button("Enregistrer");
            saveButton.setOnAction(e -> {

                String birthDate = birthdateEmployee.getText();
                String hiringDate = hiringDateEmployee.getText();
                SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat hiringDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {

                    java.util.Date parsedDateBirth = birthDateFormat.parse(birthDate);
                    Timestamp timestampBirthDate = new Timestamp(parsedDateBirth.getTime());

                    java.util.Date parsedDateHiring = birthDateFormat.parse(hiringDate);
                    Timestamp timestampHiringDate = new Timestamp(parsedDateHiring.getTime());

                    if (!isNumeric(phoneEmployee.getText()) || !isNumeric(cellphoneEmployee.getText())) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Immobilis !");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Les moldus n'utilisent que des chiffres dans leurs numéros de téléphone et pas plus de 10 !");
                        errorAlert.showAndWait();
                        return;
                    }

                    if (!isValidEmail(String.valueOf(emailEmployee))) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("L'adresse email n'est pas valide.");
                        errorAlert.showAndWait();
                        return;
                    }

                    NewEmployee newEmployee = new NewEmployee(
                            firstNameEmployee.getText(),
                            lastNameEmployee.getText(),
                            jobEmployee.getText(),
                            Integer.parseInt(idServiceSite.getText()),
                            phoneEmployee.getText(),
                            cellphoneEmployee.getText(),
                            emailEmployee.getText(),
                            timestampBirthDate,
                            timestampHiringDate,
                            Boolean.parseBoolean(employeeAdmin.getText())
                    );

                    int employeeId = Integer.parseInt(idEmployee.getText());

                    try {
                        employeeDAO.updateEmployee(employeeId, newEmployee);
                        System.out.println("Sorcier mis à jour avec succès !");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.err.println("Erreur lors de la mise à jour du sorcier : " + ex.getMessage());
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    System.err.println("Erreur lors de la conversion de la date : " + ex.getMessage());
                }
            });

            AnchorPane container = (AnchorPane) idServiceSite.getParent();
            container.getChildren().add(saveButton);
            saveButton.setStyle("-fx-background-color : #14A1D9;" +
                    "-fx-background-radius : 15px;" +
                    "-fx-text-fill: white;");
            saveButton.setPrefHeight(43);
            saveButton.setPrefWidth(120);
            AnchorPane.setTopAnchor(saveButton, 458.0);
            AnchorPane.setLeftAnchor(saveButton, 340.0);
        } else {
            buttonUpdateEmployee.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Visiblement tu n'as pas passé tes ASPIC, tu ne peux rien modifier par ici !");
            alert.showAndWait();
        }
    }

    public void initData(Employee employee) {
        selectedEmployee = employee;
        int serviceId = employee.getServiceEmployee();

        ServiceSite serviceSite = serviceDAO.getServiceSiteById(serviceId);
        if (serviceSite != null) {
            idServiceSite.setText(String.valueOf(serviceSite.getIdService()));
            nameServiceSite.setText(serviceSite.getNameService());
            idWorksite.setText(String.valueOf(serviceSite.getIdWorksite()));
            int addressId = serviceSite.getIdAddress();
            addressEmployee.setText(String.valueOf(addressId));

            Address address = addressDAO.getAddressById(addressId);
            if (address != null) {
                addressEmployee.setText(String.valueOf(address.getIdAddress()));
                buildingFloor.setText(String.valueOf(address.getBuildingFloor()));
                buildingName.setText(address.getBuildingName());
                lineAddress1.setText(address.getLineAddress1());
                lineAddress2.setText(address.getLineAddress2());
                departmentCode.setText(String.valueOf(address.getDepartmentCode()));
                cityName.setText(address.getCityName());
            } else {

            }

            int worksiteId = serviceSite.getIdWorksite();
            Worksite worksite = worksiteDAO.getWorksiteById(worksiteId);
            if (worksite != null) {
                idWorksite.setText(String.valueOf(worksite.getIdWorksite()));
                nameWorksite.setText(worksite.getNameWorksite());
            } else {

            }
        } else {

        }

        idEmployee.setText(String.valueOf(employee.getIdEmployee()));
        firstNameEmployee.setText(employee.getFirstName());
        lastNameEmployee.setText(employee.getLastName());
        jobEmployee.setText(employee.getJobEmployee());
        phoneEmployee.setText(employee.getPhoneEmployee());
        cellphoneEmployee.setText(employee.getCellphoneEmployee());
        emailEmployee.setText(employee.getEmailEmployee());
        hiringDateEmployee.setText(String.valueOf(employee.getHiringDate()));
        birthdateEmployee.setText(String.valueOf(employee.getBirthDate()));
        employeeAdmin.setText(String.valueOf(employee.isAdminApplication()));

        idEmployee.setEditable(false);
        firstNameEmployee.setEditable(false);
        lastNameEmployee.setEditable(false);
        jobEmployee.setEditable(false);
        phoneEmployee.setEditable(false);
        cellphoneEmployee.setEditable(false);
        emailEmployee.setEditable(false);
        hiringDateEmployee.setEditable(false);
        addressEmployee.setEditable(false);
        birthdateEmployee.setEditable(false);
        buildingFloor.setEditable(false);
        buildingName.setEditable(false);
        lineAddress1.setEditable(false);
        lineAddress2.setEditable(false);
        departmentCode.setEditable(false);
        cityName.setEditable(false);
        idWorksite.setEditable(false);
        nameWorksite.setEditable(false);
        idServiceSite.setEditable(false);
        nameServiceSite.setEditable(false);
    }

    private boolean isNumeric(String stringType) {
        return stringType != null && stringType.matches("\\d+") && stringType.length() == 10;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
