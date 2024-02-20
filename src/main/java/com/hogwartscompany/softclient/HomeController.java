package com.hogwartscompany.softclient;

import com.hogwartscompany.softclient.model.UserSession;

public class HomeController {

    public void initialize() {
        boolean isAdmin = UserSession.getInstance().isAdmin();
    }
}
