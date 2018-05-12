package cz.cvut.fel.matyapav.afandroid.utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFAndroidBuildException extends Exception {

    public AFAndroidBuildException() {
        super();
    }

    public AFAndroidBuildException(String message) {
        super(message);
    }

    public AFAndroidBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public AFAndroidBuildException(Throwable cause) {
        super(cause);
    }
}
