package com.tomscz.afswinx.rest.connection;

import java.net.ConnectException;

import org.apache.http.HttpHost;

public class AFConnector<T> extends BaseConnector {


    private HttpHost host = null;
    private String parameter;
    private Class<T> type;

    public AFConnector(AFSwinxConnection connection,Class<T> type) {
        this.host =
                new HttpHost(connection.getAddress(), connection.getPort(),
                        connection.getProtocol());
        this.parameter = connection.getParameters();
        this.accept = connection.getAcceptedType();
        this.contentType = connection.getContentType();
        this.type = type;
    }

    @Override
    public HttpHost getHost() {
        return host;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getContent() throws ConnectException {
        return super.getContent(type);
    }

    @Override
    public String getParameter() {
        return parameter;
    }

}
