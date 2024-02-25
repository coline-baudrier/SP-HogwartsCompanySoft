package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.model.UserSession;

public class HomeController {

    //Initialisation de la connexion sur la page d'accueil pour savoir si c'est un admin ou pas
    public void initialize() {
        boolean isAdmin = UserSession.getInstance().isAdmin();
    }
}
