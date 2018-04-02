package com.tomscz.afswinx.component.uiproxy;

import com.tomscz.afswinx.rest.connection.Device;

import java.io.IOException;

/**
 * Setup for UI proxy. User of this framework must provide URL location of Ui proxy and set uuid of application
 * which will be matched with application defined on UI proxy and will give access to application resources like
 * screens, components, business cases etc.
 */
public abstract class UIProxySetup {

    private String uiProxyApplicationUuid;
    private String uiProxyUrl;
    private Device deviceType;

    /**
     * Load ui proxy application uuid into uiProxyApplicationUuid field.
     * This uuid is generated on UI proxy and administrator of proxy should provide it for developers.
     * Can be accessed later via getUiProxyApplicationUuid method
     *
     * @return string containing application uuid
     */
    protected abstract String loadUIProxyApplicationUuid();

    /**
     * Loads ui proxy url into uiProxyUrl field. Framework will use this to make requests to proper location of UIProxy.
     * This url should in following format - protocol://www.hostname:port/contextPath where www is optional, hostname
     * can be something like example.com, port is optional as well and contextPath can be something like UIxy/api/
     * @return proxy url
     */
    protected abstract String loadUIProxyUrl();

    /**
     * Loads device type into deviceType field;
     * @return deviceType
     */
    protected abstract Device loadDeviceType();

    /**
     * Gets ui proxy url.
     *
     * @return the ui proxy url
     */
    public String getUiProxyUrl() {
        if(uiProxyUrl == null || uiProxyUrl.isEmpty()) {
            uiProxyUrl = loadUIProxyUrl();
        }
        return uiProxyUrl;
    }

    /**
     * Gets ui proxy application uuid.
     *
     * @return the ui proxy application uuid
     * @throws IOException the io exception
     */
    public String getUiProxyApplicationUuid() {
        if (uiProxyApplicationUuid == null || uiProxyApplicationUuid.isEmpty()) {
            uiProxyApplicationUuid = loadUIProxyApplicationUuid();
        }
        return uiProxyApplicationUuid;
    }

    public Device getDeviceType() {
        if(deviceType == null){
            deviceType = loadDeviceType();
        }
        return deviceType;
    }
}
