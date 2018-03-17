package cz.cvut.fel.matyapav.showcase.security;

import java.util.HashMap;

/**
 * This is concrete implementation of security.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 *
 * @since 1.0.0.
 */
public class ShowcaseSecurity implements SecurityContext {

    private String userName;
    private String password;
    private boolean loggedIn = false;

    public ShowcaseSecurity(String userName, String password, boolean isLoggedIn) {
        this.userName = userName;
        this.password = password;
        this.loggedIn = isLoggedIn;
    }

    @Override
    public boolean isUserLogged() {
        return loggedIn;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getUserPassword() {
        return password;
    }

    @Override
    public HashMap<String, String> getUserCredentials() {
        HashMap<String, String> security = new HashMap<String, String>();
        security.put("username", userName);
        security.put("password", password);
        return security;
    }

}