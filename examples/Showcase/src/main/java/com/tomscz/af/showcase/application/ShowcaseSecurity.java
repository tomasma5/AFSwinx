package com.tomscz.af.showcase.application;

import java.util.HashMap;

/**
 * This is concrete implementation of security.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ShowcaseSecurity implements SecurityContext {
    
    private String userName;
    private String password;
    private boolean isValid = false;

    public ShowcaseSecurity(String userName, String password, boolean isValid) {
        this.userName = userName;
        this.password = password;
        this.isValid = isValid;
    }

    @Override
    public boolean isUserLogged() {
        return isValid;
    }

    @Override
    public String getUserLogin() {
        return userName;
    }

    @Override
    public String getUserPassword() {
        return password;
    }

    @Override
    public HashMap<String, String> getUserNameAndPasswodr() {
        HashMap<String, String> security = new HashMap<String, String>();
        security.put("username", userName);
        security.put("password", password);
        return security;
    }

}
