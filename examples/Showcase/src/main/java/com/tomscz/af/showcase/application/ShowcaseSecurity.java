package com.tomscz.af.showcase.application;

public class ShowcaseSecurity implements SecurityContext{

    private String userName;
    private String password;
    private boolean isValid;
    
    public ShowcaseSecurity(String userName, String password, boolean isValid){
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

    
    
}
