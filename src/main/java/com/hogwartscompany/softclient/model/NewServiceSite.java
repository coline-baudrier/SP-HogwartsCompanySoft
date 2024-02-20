package com.hogwartscompany.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class NewServiceSite {
    private final SimpleStringProperty nameService;
    private final SimpleStringProperty typeService;
    private final SimpleStringProperty phoneService;
    private final SimpleStringProperty emailService;
    private final SimpleObjectProperty<Timestamp> dateCreationService;
    private final SimpleIntegerProperty idAddress;
    private final SimpleIntegerProperty idWorksite;

    public NewServiceSite(
            @JsonProperty("nameService") String nameService,
            @JsonProperty("typeService") String typeService,
            @JsonProperty("phoneService") String phoneService,
            @JsonProperty("emailService") String emailService,
            @JsonProperty("dateCreationService") Timestamp dateCreationService,
            @JsonProperty("idAddress") int idAddress,
            @JsonProperty("idWorksite") int idWorksite
    ) {
        this.nameService = new SimpleStringProperty(nameService);
        this.typeService = new SimpleStringProperty(typeService);
        this.phoneService = new SimpleStringProperty(phoneService);
        this.emailService = new SimpleStringProperty(emailService);
        this.dateCreationService = new SimpleObjectProperty<>(dateCreationService);
        this.idAddress = new SimpleIntegerProperty(idAddress);
        this.idWorksite = new SimpleIntegerProperty(idWorksite);
    }


    public String getNameService() {
        return nameService.get();
    }

    public SimpleStringProperty nameServiceProperty() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService.set(nameService);
    }

    public String getTypeService() {
        return typeService.get();
    }

    public SimpleStringProperty typeServiceProperty() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService.set(typeService);
    }

    public String getPhoneService() {
        return phoneService.get();
    }

    public SimpleStringProperty phoneServiceProperty() {
        return phoneService;
    }

    public void setPhoneService(String phoneService) {
        this.phoneService.set(phoneService);
    }

    public String getEmailService() {
        return emailService.get();
    }

    public SimpleStringProperty emailServiceProperty() {
        return emailService;
    }

    public void setEmailService(String emailService) {
        this.emailService.set(emailService);
    }


    public int getIdAddress() {
        return idAddress.get();
    }

    public SimpleIntegerProperty idAddressProperty() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress.set(idAddress);
    }

    public int getIdWorksite() {
        return idWorksite.get();
    }

    public SimpleIntegerProperty idWorksiteProperty() {
        return idWorksite;
    }

    public void setIdWorksite(int idWorksite) {
        this.idWorksite.set(idWorksite);
    }

    public Timestamp getDateCreationService() {
        return dateCreationService.get();
    }

    public SimpleObjectProperty<Timestamp> dateCreationServiceProperty() {
        return dateCreationService;
    }

    public void setDateCreationService(Timestamp dateCreationService) {
        this.dateCreationService.set(dateCreationService);
    }
}
