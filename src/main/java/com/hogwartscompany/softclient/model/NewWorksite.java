package com.hogwartscompany.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewWorksite {
    private final SimpleStringProperty nameWorksite;
    private final SimpleStringProperty typeWorksite;
    private final SimpleStringProperty phoneWorksite;
    private final SimpleStringProperty emailWorksite;
    private final SimpleIntegerProperty idAddress;

    public NewWorksite(
            @JsonProperty("nameWorksite") String nameWorksite,
            @JsonProperty("typeWorksite") String typeWorksite,
            @JsonProperty("phoneWorksite") String phoneWorksite,
            @JsonProperty("emailWorksite") String emailWorksite,
            @JsonProperty("idAddress") int idAddress
    ) {
        this.nameWorksite = new SimpleStringProperty(nameWorksite);
        this.typeWorksite = new SimpleStringProperty(typeWorksite);
        this.phoneWorksite = new SimpleStringProperty(phoneWorksite);
        this.emailWorksite = new SimpleStringProperty(emailWorksite);
        this.idAddress = new SimpleIntegerProperty(idAddress);
    }

    public String getNameWorksite() {
        return nameWorksite.get();
    }

    public SimpleStringProperty nameWorksiteProperty() {
        return nameWorksite;
    }

    public void setNameWorksite(String nameWorksite) {
        this.nameWorksite.set(nameWorksite);
    }

    public String getTypeWorksite() {
        return typeWorksite.get();
    }

    public SimpleStringProperty typeWorksiteProperty() {
        return typeWorksite;
    }

    public void setTypeWorksite(String typeWorksite) {
        this.typeWorksite.set(typeWorksite);
    }

    public String getPhoneWorksite() {
        return phoneWorksite.get();
    }

    public SimpleStringProperty phoneWorksiteProperty() {
        return phoneWorksite;
    }

    public void setPhoneWorksite(String phoneWorksite) {
        this.phoneWorksite.set(phoneWorksite);
    }

    public String getEmailWorksite() {
        return emailWorksite.get();
    }

    public SimpleStringProperty emailWorksiteProperty() {
        return emailWorksite;
    }

    public void setEmailWorksite(String emailWorksite) {
        this.emailWorksite.set(emailWorksite);
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
}
