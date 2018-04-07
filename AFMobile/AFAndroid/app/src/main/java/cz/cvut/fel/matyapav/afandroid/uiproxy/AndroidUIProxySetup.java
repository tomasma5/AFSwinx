package cz.cvut.fel.matyapav.afandroid.uiproxy;


import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.enums.uiproxy.Device;

/**
 * Setup for UI proxy. User of this framework must provide URL location of Ui proxy and set uuid of application
 * which will be matched with application defined on UI proxy and will give access to application resources like
 * screens, components, business cases etc.
 */
public abstract class AndroidUIProxySetup {

    private String uiProxyApplicationUuid;
    private String uiProxyUrl;
    private Device deviceType;
    private String deviceIdentifier;
    private String user;
    private String lastScreenKey;

    /**
     * Load ui proxy application uuid into uiProxyApplicationUuid field.
     * This uuid is generated on UI proxy and administrator of proxy should provide it for developers.
     * Can be accessed later via getUiProxyApplicationUuid method
     *
     * @param context android context
     * @return string containing application uuid
     */
    protected abstract String loadUIProxyApplicationUuid(Context context);

    /**
     * Loads ui proxy url into uiProxyUrl field. Framework will use this to make requests to proper location of UIProxy.
     * This url should in following format - protocol://www.hostname:port/contextPath where www is optional, hostname
     * can be something like example.com, port is optional as well and contextPath can be something like UIxy/api/
     *
     * @param context android context
     * @return proxy url
     */
    protected abstract String loadUIProxyUrl(Context context);

    /**
     * Loads device type into deviceType field;
     *
     * @param context android context
     * @return deviceType
     */
    protected abstract Device loadDeviceType(Context context);

    /**
     * Loads device identifier (most likely mac address of device if not defined alternatively)
     *
     * @param context android context
     * @return device identifier
     */
    protected abstract String loadDeviceIdentifier(Context context);

    /**
     * Gets ui proxy url.
     *
     * @param context android context
     * @return the ui proxy url
     */
    public String getUiProxyUrl(Context context) {
        if (uiProxyUrl == null || uiProxyUrl.isEmpty()) {
            uiProxyUrl = loadUIProxyUrl(context);
        }
        return uiProxyUrl;
    }

    /**
     * Gets ui proxy application uuid.
     *
     * @param context android context
     * @return the ui proxy application uuid
     */
    public String getUiProxyApplicationUuid(Context context) {
        if (uiProxyApplicationUuid == null || uiProxyApplicationUuid.isEmpty()) {
            uiProxyApplicationUuid = loadUIProxyApplicationUuid(context);
        }
        return uiProxyApplicationUuid;
    }

    /**
     * Gets device type - used by ui proxy in field filtration
     *
     * @param context android context
     * @return type of device
     */
    public Device getDeviceType(Context context) {
        if (deviceType == null) {
            deviceType = loadDeviceType(context);
        }
        return deviceType;
    }

    /**
     * Gets device identifier - most likely mac address of device
     *
     * @param context android context
     * @return device identifier
     */
    public String getDeviceIdentifier(Context context) {
        if (deviceIdentifier == null || deviceIdentifier.isEmpty()) {
            deviceIdentifier = loadDeviceIdentifier(context);
        }
        return deviceIdentifier;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLastScreenKey() {
        return lastScreenKey;
    }

    public void setLastScreenKey(String lastScreenKey) {
        this.lastScreenKey = lastScreenKey;
    }
}
