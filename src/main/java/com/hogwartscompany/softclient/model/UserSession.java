package com.hogwartscompany.softclient.model;

public class UserSession {
    private static UserSession instance;
    private boolean isAdmin;

    private UserSession() {
        isAdmin = false;
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
