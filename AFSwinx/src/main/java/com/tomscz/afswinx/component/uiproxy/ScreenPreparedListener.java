package com.tomscz.afswinx.component.uiproxy;

import java.util.EventListener;

/**
 * Listens for the end of screen preparation
 */
public interface ScreenPreparedListener extends EventListener {

    /**
     * Called when screen is prepared
     * @param proxyScreenDefinition screen definition
     */
    public void onScreenPrepared(AFProxyScreenDefinition proxyScreenDefinition);

}
