package com.hogwartscompany.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.security.Timestamp;

public class NewEmployee {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty jobEmployee;
    private final SimpleIntegerProperty serviceEmployee;
    private final SimpleStringProperty phoneEmployee;
    private final SimpleStringProperty cellphoneEmployee;
    private final SimpleStringProperty emailEmployee;
    private final SimpleObjectProperty<Timestamp> birthDate;
    private final SimpleObjectProperty<Timestamp> hiringDate;
    private final SimpleBooleanProperty adminApplication;

    public NewEmployee(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("jobEmployee") String jobEmployee,
            @JsonProperty("serviceEmployee") int serviceEmployee,
            @JsonProperty("phoneEmployee") String phoneEmployee,
            @JsonProperty("cellphoneEmployee") String cellphoneEmployee,
            @JsonProperty("emailEmployee") String emailEmployee,
            @JsonProperty("birthDate") Timestamp birthDate,
            @JsonProperty("hiringDate") Timestamp hiringDate,
            @JsonProperty("adminApplication") boolean adminApplication) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.jobEmployee = new SimpleStringProperty(jobEmployee);
        this.serviceEmployee = new SimpleIntegerProperty(serviceEmployee);
        this.phoneEmployee = new SimpleStringProperty(phoneEmployee);
        this.cellphoneEmployee = new SimpleStringProperty(cellphoneEmployee);
        this.emailEmployee = new SimpleStringProperty(emailEmployee);
        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.hiringDate = new SimpleObjectProperty<>(hiringDate);
        this.adminApplication = new SimpleBooleanProperty(adminApplication);
    }
    
    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getJobEmployee() {
        return jobEmployee.get();
    }

    public SimpleStringProperty jobEmployeeProperty() {
        return jobEmployee;
    }

    public void setJobEmployee(String jobEmployee) {
        this.jobEmployee.set(jobEmployee);
    }

    public int getServiceEmployee() {
        return serviceEmployee.get();
    }

    public SimpleIntegerProperty serviceEmployeeProperty() {
        return serviceEmployee;
    }

    public void setServiceEmployee(int serviceEmployee) {
        this.serviceEmployee.set(serviceEmployee);
    }

    public String getPhoneEmployee() {
        return phoneEmployee.get();
    }

    public SimpleStringProperty phoneEmployeeProperty() {
        return phoneEmployee;
    }

    public void setPhoneEmployee(String phoneEmployee) {
        this.phoneEmployee.set(phoneEmployee);
    }

    public String getCellphoneEmployee() {
        return cellphoneEmployee.get();
    }

    public SimpleStringProperty cellphoneEmployeeProperty() {
        return cellphoneEmployee;
    }

    public void setCellphoneEmployee(String cellphoneEmployee) {
        this.cellphoneEmployee.set(cellphoneEmployee);
    }

    public String getEmailEmployee() {
        return emailEmployee.get();
    }

    public SimpleStringProperty emailEmployeeProperty() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee.set(emailEmployee);
    }

    public Timestamp getBirthDate() {
        return birthDate.get();
    }

    public SimpleObjectProperty<Timestamp> birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate.set(birthDate);
    }

    public Timestamp getHiringDate() {
        return hiringDate.get();
    }

    public SimpleObjectProperty<Timestamp> hiringDateProperty() {
        return hiringDate;
    }

    public void setHiringDate(Timestamp hiringDate) {
        this.hiringDate.set(hiringDate);
    }

    public boolean isAdminApplication() {
        return adminApplication.get();
    }

    public SimpleBooleanProperty adminApplicationProperty() {
        return adminApplication;
    }

    public void setAdminApplication(boolean adminApplication) {
        this.adminApplication.set(adminApplication);
    }
}
