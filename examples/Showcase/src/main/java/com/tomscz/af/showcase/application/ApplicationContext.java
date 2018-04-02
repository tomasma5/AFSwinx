package com.tomscz.af.showcase.application;

import com.tomscz.afswinx.component.uiproxy.UIProxySetup;
import com.tomscz.afswinx.rest.connection.Device;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This is application context of this application. It holds variable and
 * settings, which will be used in entire application
 *
 * @author Martin Tomasek (martin@toms-cz.com)
 * @since 1.0.0.
 */
public class ApplicationContext extends UIProxySetup {

    private static final String APP_CONFIG_FILE = "application.properties";

    private static ApplicationContext instance;

    // private SecurityContext securityContext = new
    // ShowcaseSecurity("sa2","jaina",true);

    private SecurityContext securityContext;


    private ResourceBundle localization;

    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public void changeLocalization(String localizationName)
            throws FileNotFoundException {
        ResourceBundle tempLocalization = ResourceBundle
                .getBundle(localizationName);
        if (tempLocalization != null) {
            localization = tempLocalization;
        } else {
            throw new FileNotFoundException("The localization file named "
                    + localizationName + " was not found.");
        }
    }

    @Override
    public String loadUIProxyApplicationUuid() {
        Properties applicationProperties = new Properties();
        InputStream propInput = getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE);
        try {
            applicationProperties.load(propInput);
            return applicationProperties.getProperty("proxy.uuid");
        } catch (IOException e) {
            System.err.println("cannot get ui proxy application uuid from " + APP_CONFIG_FILE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String loadUIProxyUrl() {
        Properties applicationProperties = new Properties();
        InputStream propInput = getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE);
        try {
            applicationProperties.load(propInput);
            return applicationProperties.getProperty("proxy.url");
        } catch (IOException e) {
            System.err.println("cannot get ui proxy url from " + APP_CONFIG_FILE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Device loadDeviceType() {
        return Device.PC;
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
