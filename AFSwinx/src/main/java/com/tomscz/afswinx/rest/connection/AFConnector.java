package com.tomscz.afswinx.rest.connection;

import java.net.ConnectException;

import org.apache.http.HttpHost;

/**
 * This class is concrete implementation of {@link BaseConnector}. It can do request on server. Use {@link AFConnector#doRequest(String)} method.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 * @param <T> type of object which will be returned.
 */
public class AFConnector<T> extends BaseConnector {

    private HttpHost host = null;
    private String parameter;
    private Class<T> type;

    public AFConnector(AFSwinxConnection connection, Class<T> type) {
        this.host =
                new HttpHost(connection.getAddress(), connection.getPort(),
                        connection.getProtocol());
        this.parameter = connection.getParameters();
        this.accept = connection.getAcceptedType();
        this.contentType = connection.getContentType();
        this.type = type;
        if (connection.getHttpMethod() != null) {
            this.httpMethod = connection.getHttpMethod();
        }
        this.headersParams = connection.getHeaderParams();
        this.security = connection.getSecurity();
    }

    @Override
    public HttpHost getHost() {
        return host;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T doRequest(String body) throws ConnectException {
        return super.doRequest(type, body);
    }

    @Override
    public String getParameter() {
        return parameter;
    }

}
