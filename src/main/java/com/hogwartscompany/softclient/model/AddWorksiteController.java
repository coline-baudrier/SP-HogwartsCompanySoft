package com.hogwartscompany.softclient.model;

import com.hogwartscompany.softclient.dao.WorksiteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddWorksiteController {
    private final WorksiteDAO worksiteDAO;

    @FXML
    public TextField idField;

    @FXML
    public TextField nameField;

    @FXML
    public TextField typeField;

    @FXML
    public TextField phoneField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField idAddressField;

    public AddWorksiteController() {
        this.worksiteDAO = new WorksiteDAO();
    }

    public void addWorksite(ActionEvent actionEvent) {
        int idWorksite = Integer.parseInt(idField.getText()); // Récupérer la valeur d'un champ d'interface utilisateur
        String nameWorksite = nameField.getText(); // Exemple pour récupérer le nom depuis un champ de texte nommé nameField
        String typeWorksite = typeField.getText(); // Récupérer le type depuis un champ de texte nommé typeField
        String phoneWorksite = phoneField.getText(); // Récupérer le téléphone depuis un champ de texte nommé phoneField
        String emailWorksite = emailField.getText(); // Récupérer l'email depuis un champ de texte nommé emailField
        int idAddress = Integer.parseInt(idAddressField.getText()); // Récupérer l'ID de l'adresse depuis un champ de texte nommé idAddressField

        // Appeler la méthode addWorksite avec les valeurs récupérées
        addWorksiteAPI(idWorksite, nameWorksite, typeWorksite, phoneWorksite, emailWorksite, idAddress);
    }

    public void addWorksiteAPI(int idWorksite, String nameWorksite, String typeWorksite, String phoneWorksite, String emailWorksite, int idAddress) {
        Worksite worksite = new Worksite(idWorksite, nameWorksite, typeWorksite, phoneWorksite, emailWorksite, idAddress);
        try {
            worksiteDAO.createWorksite(worksite);
            System.out.println("Worksite ajouté avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du Worksite : " + e.getMessage());
        }
    }
}
