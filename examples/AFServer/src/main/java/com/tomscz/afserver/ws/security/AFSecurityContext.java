package com.tomscz.afserver.ws.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afserver.persistence.entity.UserRoles;

/**
 * This is implementation of security context in AFServer. Its showcase server side.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSecurityContext implements Serializable {

    private static final long serialVersionUID = 1L;

    List<UserRoles> userRoles = new ArrayList<UserRoles>();

    String loggedUserName;

    public AFSecurityContext(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public boolean isUserInRole(UserRoles roleToVerify) {
        for (UserRoles role : userRoles) {
            if (role.equals(roleToVerify)) {
                return true;
            }
        }
        return false;
    }

}
