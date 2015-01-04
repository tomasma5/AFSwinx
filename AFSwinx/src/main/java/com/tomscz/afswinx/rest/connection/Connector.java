package com.tomscz.afswinx.rest.connection;

import java.net.ConnectException;

/**
 * This interface specify what must be done to create request on server.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface Connector {

    /**
     * This method do request on server. On body will be body. Returned type is generic T.
     * 
     * @param body which will be send to server in request.
     * @return Type of T.
     * @throws ConnectException if during perform request is exception occur.
     */
    public <T> T doRequest(String body) throws ConnectException;

}
