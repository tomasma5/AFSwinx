package com.tomscz.af.showcase.application;

public interface SecurityContext {

    public boolean isUserLogged();
    
    public String getUserLogin();
    
    public String getUserPassword();
    
}
