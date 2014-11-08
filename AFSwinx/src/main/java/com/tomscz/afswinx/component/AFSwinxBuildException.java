package com.tomscz.afswinx.component;

/**
 * This is exception which should be thrown when component cannot be build.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxBuildException extends Exception {

    private static final long serialVersionUID = 1L;

    public AFSwinxBuildException(String message) {
        super(message);
    }

}
