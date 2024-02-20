package com.hogwartscompany.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewAddress {
    private final SimpleStringProperty buildingFloor;
    private final SimpleStringProperty buildingName;
    private final SimpleStringProperty lineAddress1;
    private final SimpleStringProperty lineAddress2;
    private final SimpleStringProperty departmentCode;
    private final SimpleStringProperty cityName;

    public NewAddress(
            @JsonProperty("buildingFloor") String buildingFloor,
            @JsonProperty("buildingName") String buildingName,
            @JsonProperty("lineAddress1") String lineAddress1,
            @JsonProperty("lineAddress2") String lineAddress2,
            @JsonProperty("departmentCode") String departmentCode,
            @JsonProperty("cityName") String cityName) {
        this.buildingFloor = new SimpleStringProperty(buildingFloor);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.lineAddress1 = new SimpleStringProperty(lineAddress1);
        this.lineAddress2 = new SimpleStringProperty(lineAddress2);
        this.departmentCode = new SimpleStringProperty(departmentCode);
        this.cityName = new SimpleStringProperty(cityName);
    }

    public String getBuildingFloor() {
        return buildingFloor.get();
    }

    public SimpleStringProperty buildingFloorProperty() {
        return buildingFloor;
    }

    public void setBuildingFloor(String buildingFloor) {
        this.buildingFloor.set(buildingFloor);
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

    public String getDepartmentCode() {
        return departmentCode.get();
    }

    public SimpleStringProperty departmentCodeProperty() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode.set(departmentCode);
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
