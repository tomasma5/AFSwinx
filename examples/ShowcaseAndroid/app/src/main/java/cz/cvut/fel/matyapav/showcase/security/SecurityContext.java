package cz.cvut.fel.matyapav.showcase.security;

import java.util.HashMap;

/**
 * This is security context interface which is used to work with logged user.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 *
 * @since 1.0.0.
 */
public interface SecurityContext {

    /**
     * Return true if user is logged.
     *
     * @return True if user is logged, false otherwise.
     */
    public boolean isUserLogged();

    /**
     * This method return logged user username.
     *
     * @return Username of logged user.
     */
    public String getUsername();

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
    public HashMap<String, String> getUserCredentials();

}