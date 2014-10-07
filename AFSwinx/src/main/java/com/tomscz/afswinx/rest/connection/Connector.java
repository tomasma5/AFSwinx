package com.tomscz.afswinx.rest.connection;

import java.net.ConnectException;

public interface Connector {
    
    public <T> T getContent() throws ConnectException;

}
