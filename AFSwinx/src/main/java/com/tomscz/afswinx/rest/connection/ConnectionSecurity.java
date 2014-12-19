package com.tomscz.afswinx.rest.connection;

/**
 * This class hold information about method, username and password. It is used when client create
 * request to server and there is secured source.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ConnectionSecurity {

    private SecurityMethod method = SecurityMethod.BASIC;
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SecurityMethod getMethod() {
        return method;
    }

    public void setMethod(SecurityMethod method) {
        this.method = method;
    }

}
