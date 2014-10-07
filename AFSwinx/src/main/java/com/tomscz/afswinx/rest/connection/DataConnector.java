package com.tomscz.afswinx.rest.connection;

import org.apache.http.HttpHost;

//TODO add support for get data to fill metamodel
public class DataConnector extends BaseConnector {

    private HttpHost host = null;
    private String parameter = null;
    
    public DataConnector(String hostName, String parameter, int port, HeaderType accept, HeaderType contentType) {
        this.host = new HttpHost(hostName, port, BaseConnector.HTTP_PROTOCOL);
        this.parameter = parameter;
    }
    
    public DataConnector(String hostName, String parameter, int port) {
        this.host = new HttpHost(hostName, port, BaseConnector.HTTP_PROTOCOL);
        this.parameter = parameter;
    }

    public DataConnector(String hostName, String parameter, int port, String protocol) {
        this.host = new HttpHost(hostName, port, protocol);
        this.parameter = parameter;
    }
    
    @Override
    public <T> T getContent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpHost getHost() {
        return host;
    }

}
