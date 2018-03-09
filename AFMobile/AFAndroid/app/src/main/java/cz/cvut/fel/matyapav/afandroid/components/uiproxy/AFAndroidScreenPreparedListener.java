package cz.cvut.fel.matyapav.afandroid.components.uiproxy;

import java.util.EventListener;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public interface AFAndroidScreenPreparedListener extends EventListener{

    public void onScreenPrepared(AFAndroidProxyScreenDefinition proxyScreenDefinition);
}
