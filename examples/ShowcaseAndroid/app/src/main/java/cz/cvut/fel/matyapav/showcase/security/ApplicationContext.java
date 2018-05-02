package cz.cvut.fel.matyapav.showcase.security;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.enums.uiproxy.Device;
import cz.cvut.fel.matyapav.afandroid.uiproxy.AndroidUIProxySetup;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.util.NetworkUtils;
import cz.cvut.fel.matyapav.showcase.fragments.BaseFragment;

/**
 * This is application context of this application. It holds variable and
 * settings, which will be used in entire application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 * @since 1.0.0.
 */
public class ApplicationContext extends AndroidUIProxySetup {

    public static final String APP_CONFIG_FILE = "application.properties";

    private static ApplicationContext instance;

    private SecurityContext securityContext;

    private BaseFragment currentFragment;

    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    protected String loadUIProxyApplicationUuid(Context context) {
        Properties applicationProperties = new Properties();
        try {
            InputStream propInput = context.getAssets().open(APP_CONFIG_FILE);
            applicationProperties.load(propInput);
            return applicationProperties.getProperty("proxy.uuid");
        } catch (IOException e) {
            Log.e(ApplicationContext.class.toString(), "Cannot get ui proxy application uuid from " + APP_CONFIG_FILE);
            e.printStackTrace();
        }
        return null;
    }


    protected String loadUIProxyUrl(Context context) {
        Properties applicationProperties = new Properties();
        try {
            InputStream propInput = context.getAssets().open(APP_CONFIG_FILE);
            applicationProperties.load(propInput);
            return applicationProperties.getProperty("proxy.url");
        } catch (IOException e) {
            Log.e(ApplicationContext.class.toString(), "Cannot get ui proxy url uuid from " + APP_CONFIG_FILE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Device loadDeviceType(Context context) {
        return Utils.deviceHasTabletSize(context) ? Device.TABLET : Device.PHONE;
    }

    @Override
    protected String loadDeviceIdentifier(Context context) {
        return NetworkUtils.getMacAddress();
    }

    @Override
    protected String loadNearbyAppUrl(Context context) {
        Properties applicationProperties = new Properties();
        try {
            InputStream propInput = context.getAssets().open(APP_CONFIG_FILE);
            applicationProperties.load(propInput);
            return applicationProperties.getProperty("nearby.rest.url");
        } catch (IOException e) {
            Log.e(ApplicationContext.class.toString(), "Cannot get ui proxy url uuid from " + APP_CONFIG_FILE);
            e.printStackTrace();
        }
        return null;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        if (securityContext == null) {
            AFAndroid.getInstance().getProxySetup().setUser(null);
        } else {
            AFAndroid.getInstance().getProxySetup().setUser(securityContext.getUsername());
        }
        this.securityContext = securityContext;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
