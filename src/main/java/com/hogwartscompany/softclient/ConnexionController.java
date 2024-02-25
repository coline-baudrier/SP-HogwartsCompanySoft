package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.security.Key;

import static javafx.scene.input.KeyCode.*;

public class ConnexionController {

    @FXML
    public Button buttonGoToHomePage;

    @FXML
    private void initialize() {

    }

    @FXML
    private void pressButtonConnexion (ActionEvent event) {
        //Pression du bouton pour la connexion
        UserSession userSession = UserSession.getInstance();

        if (!userSession.isAdmin()) {
            System.out.println(UserSession.getInstance().isAdmin());
            goToHomePage();
        }
    }

    @FXML
    void goToHomePage() {
        //Envoie vers la page d'accueil
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToHomePage.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        //Gestion de l'appui des touches avec KeyEvent
        if (event.isControlDown() && event.getCode() == KeyCode.TAB) {
            String adminPassword = "Rictusempra";

            TextInputDialog askPassAdmin = new TextInputDialog();
            askPassAdmin.setTitle("Sorcier diplômé ?");
            askPassAdmin.setContentText("Donne moi le mot de passe pour entrer dans la maison des Admin : ");
            //Vérification du mot de passe tapé
            askPassAdmin.showAndWait().ifPresent(passwordPrint -> {
                if (passwordPrint.equals(adminPassword)) {
                    UserSession.getInstance().setAdmin(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Je peux te laisser entrer");
                    alert.setHeaderText(null);
                    alert.setContentText("Il semblerait que tu as réussi tes ASPICs, bien joué !");
                    System.out.println(UserSession.getInstance().isAdmin());
                    goToHomePage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Revelio");
                    alert.setHeaderText(null);
                    alert.setContentText("Le mot de passe entré est incorrect : t'es vraiment un sorcier diplômé ?");
                    alert.showAndWait();
                }
            });
        }
    }
}
