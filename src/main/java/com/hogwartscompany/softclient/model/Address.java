package com.hogwartscompany.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {
   private final SimpleIntegerProperty idAddress;
    private final SimpleIntegerProperty buildingFloor;
    private final SimpleStringProperty buildingName;
    private final SimpleStringProperty lineAddress1;
    private final SimpleStringProperty lineAddress2;
    private final SimpleIntegerProperty departmentCode;
    private final SimpleStringProperty cityName;

    public Address(
            @JsonProperty("idAddress") int idAddress,
            @JsonProperty("buildingFloor") int buildingFloor,
            @JsonProperty("buildingName") String buildingName,
            @JsonProperty("lineAddress1") String lineAddress1,
            @JsonProperty("lineAddress2") String lineAddress2,
            @JsonProperty("departmentCode") int departmentCode,
            @JsonProperty("cityName") String cityName) {
        this.idAddress = new SimpleIntegerProperty(idAddress);
        this.buildingFloor = new SimpleIntegerProperty(buildingFloor);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.lineAddress1 = new SimpleStringProperty(lineAddress1);
        this.lineAddress2 = new SimpleStringProperty(lineAddress2);
        this.departmentCode = new SimpleIntegerProperty(departmentCode);
        this.cityName = new SimpleStringProperty(cityName);
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

    public int getBuildingFloor() {
        return buildingFloor.get();
    }

    public SimpleIntegerProperty buildingFloorProperty() {
        return buildingFloor;
    }

    public void setBuildingFloor(String buildingFloor) {
        this.buildingFloor.set(Integer.parseInt(buildingFloor));
    }

    public String getBuildingName() {
        return buildingName.get();
    }

    public SimpleStringProperty buildingNameProperty() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName.set(buildingName);
    }

    public String getLineAddress1() {
        return lineAddress1.get();
    }

    public SimpleStringProperty lineAddress1Property() {
        return lineAddress1;
    }

    public void setLineAddress1(String lineAddress1) {
        this.lineAddress1.set(lineAddress1);
    }

    public String getLineAddress2() {
        return lineAddress2.get();
    }

    public SimpleStringProperty lineAddress2Property() {
        return lineAddress2;
    }

    public void setLineAddress2(String lineAddress2) {
        this.lineAddress2.set(lineAddress2);
    }

    public int getDepartmentCode() {
        return departmentCode.get();
    }

    public SimpleIntegerProperty departmentCodeProperty() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode.set(Integer.parseInt(departmentCode));
    }

    public String getCityName() {
        return cityName.get();
    }

    public SimpleStringProperty cityNameProperty() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }
}
