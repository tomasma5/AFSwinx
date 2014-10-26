package com.tomscz.afswinx.rest.connection;

/**
 * This is exception which is thrown during communicate with server or during creating process which
 * leads to communication.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxConnectionException extends Exception {

    private static final long serialVersionUID = 1L;

    public AFSwinxConnectionException(String message) {
        super(message);
    }

}
