package com.tomscz.af.showcase.application;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * This is application context of this application. It holds variable and settings, which will be
 * used in entire application
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ApplicationContext {

    private static ApplicationContext instance;
    
    private SecurityContext securityContext = new ShowcaseSecurity("sa2","jaina",true);

    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private ResourceBundle localization;

    public void changeLocalization(String localizationName) throws FileNotFoundException {
        ResourceBundle tempLocalization = ResourceBundle.getBundle(localizationName);
        if (tempLocalization != null) {
            localization = tempLocalization;
        } else {
            throw new FileNotFoundException("The localization file named " + localizationName
                    + " was not found.");
        }
    }

    public ResourceBundle getLocalization() {
        return localization;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

}
