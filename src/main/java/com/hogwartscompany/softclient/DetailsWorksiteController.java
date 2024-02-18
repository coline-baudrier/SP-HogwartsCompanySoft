package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.model.Worksite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DetailsWorksiteController {

    @FXML
    private TextField addressWorksite;

    @FXML
    private Button buttonClosePopUp;

    @FXML
    private Button buttonDeleteWorksite;

    @FXML
    private Button buttonUpdateWorksite;

    @FXML
    private TextField emailWorksite;

    @FXML
    private TextField idWorksite;

    @FXML
    private TextField nameWorksite;

    @FXML
    private TextField phoneWorksite;

    @FXML
    private TextField typeWorksite;

    private Worksite selectedWorksite;

    @FXML
    void closePopUp(ActionEvent event) {

    }

    @FXML
    void deleteWorksite(ActionEvent event) {

    }

    @FXML
    void updateWorksite(ActionEvent event) {

    }

    public void initData(Worksite worksite) {
        selectedWorksite = worksite;
        idWorksite.setText(String.valueOf(worksite.getIdWorksite()));
        nameWorksite.setText(worksite.getNameWorksite());
        typeWorksite.setText(worksite.getTypeWorksite());
        phoneWorksite.setText(worksite.getPhoneWorksite());
        emailWorksite.setText(worksite.getEmailWorksite());
        addressWorksite.setText(String.valueOf(worksite.getIdAddress()));
    }

}
