package cz.cvut.fel.matyapav.showcase.security;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cz.cvut.fel.matyapav.showcase.fragments.BaseFragment;

/**
 * This is application context of this application. It holds variable and
 * settings, which will be used in entire application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 *
 * @since 1.0.0.
 */
public class ApplicationContext {

    public static final String APP_CONFIG_FILE = "application.properties";

    private static ApplicationContext instance;

    private SecurityContext securityContext;

    private String uiProxyApplicationUuid;

    private String uiProxyUrl;

    private BaseFragment currentFragment;

    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private void loadUIProxyApplicationUuid(Context context) throws IOException {
        Properties applicationProperties = new Properties();
        InputStream propInput = context.getAssets().open(APP_CONFIG_FILE);
        applicationProperties.load(propInput);
        this.uiProxyApplicationUuid = applicationProperties.getProperty("proxy.uuid");
    }


    public void loadUIProxyUrl(Context context) throws IOException {
        Properties applicationProperties = new Properties();
        InputStream propInput = context.getAssets().open(APP_CONFIG_FILE);
        applicationProperties.load(propInput);
        this.uiProxyUrl = applicationProperties.getProperty("proxy.url");
    }

    public String getUiProxyUrl() {
        return uiProxyUrl;
    }

    public String getUiProxyApplicationUuid(Context context) throws IOException {
        if(uiProxyApplicationUuid == null || uiProxyApplicationUuid.isEmpty()) {
            loadUIProxyApplicationUuid(context);
        }
        return uiProxyApplicationUuid;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
