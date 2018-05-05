package cz.cvut.fel.matyapav.afandroid.components.uiproxy;

import java.util.EventListener;

/**
 * Listens for end of screen preparation process
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public interface AFAndroidScreenPreparedListener extends EventListener{

    /**
     * Called when the screen is prepared
     *
     * @param proxyScreenDefinition definition of screen with prepared component builders
     */
    public void onScreenPrepared(AFAndroidProxyScreenDefinition proxyScreenDefinition);
}
