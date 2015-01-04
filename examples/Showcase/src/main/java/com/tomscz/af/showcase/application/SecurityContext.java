package com.tomscz.af.showcase.application;

import java.util.HashMap;

/**
 * This is security context interface which is used to work with logged user.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface SecurityContext {

    /**
     * Return if user is logged.
     * 
     * @return True if user is logged, false otherwise.
     */
    public boolean isUserLogged();

    /**
     * This method return logged user username.
     * 
     * @return Username of logged user.
     */
    public String getUserLogin();

    /**
     * This method return logged user password.
     * 
     * @return logged user password.
     */
    public String getUserPassword();

    /**
     * This method get logged user userName and password.
     * 
     * @return userName and password of logged user.
     */
    public HashMap<String, String> getUserNameAndPasswodr();

}
