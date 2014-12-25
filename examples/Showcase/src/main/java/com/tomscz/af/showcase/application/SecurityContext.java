package com.tomscz.af.showcase.application;

import java.util.HashMap;

public interface SecurityContext {

    public boolean isUserLogged();
    
    public String getUserLogin();
    
    public String getUserPassword();
    
    public HashMap<String, String> getUserNameAndPasswodr();
    
}
