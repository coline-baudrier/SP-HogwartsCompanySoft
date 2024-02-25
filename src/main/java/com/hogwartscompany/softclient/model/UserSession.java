package com.hogwartscompany.softclient.model;

public class UserSession {
    //Création de l'instance
    private static UserSession instance;
    //Création de la gestion d'un administrateur avec un boolean
    private boolean isAdmin;

    //De base l'admin est à faux
    private UserSession() {
        isAdmin = false;
    }

    //Synchronisation à la connexion pour dire qu'il y une nouvelle UserSession
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    //Permet de vérifier si c'est un Admin dans mes if
    public boolean isAdmin() {
        return isAdmin;
    }

    //Modifie l'admin en fonction du type de connexion
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    //A chaque déconnexion l'instance passe à null, permet de me reconnecter sans avoir à fermer la session
    public static synchronized void resetInstance() {
        instance = null;
    }
}
