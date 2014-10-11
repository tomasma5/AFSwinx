package com.tomscz.afswinx.rest.connection;

import com.tomscz.afswinx.rest.connection.BaseConnector.HeaderType;

/**
 * This class holds information which are necessary to get model or data and post them. Based on
 * this are created concrete connectors.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxConnection {

    public static final String PROTOCOL_HTTP = "http";

    //Address ex: localhost or toms-cz.com without http or https
    private String address;
    private int port;
    //Additional parameters it should exclude port, which is specify in port variable
    private String parameters;
    //Protocol ex: http or https
    private String protocol;
    //Type of expected returned value
    private HeaderType acceptedType;
    //Type of header request
    private HeaderType contentType;

    public AFSwinxConnection(String address, int port, String parameters) {
        super();
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.acceptedType = HeaderType.JSON;
        this.contentType = HeaderType.JSON;
        this.protocol = PROTOCOL_HTTP;
    }

    public AFSwinxConnection(String address, int port, String parameters, String protocol) {
        super();
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.acceptedType = HeaderType.JSON;
        this.contentType = HeaderType.JSON;
        this.protocol = protocol;
    }

    public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
            HeaderType contentType) {
        super();
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.acceptedType = acceptedType;
        this.contentType = contentType;
        this.protocol = PROTOCOL_HTTP;
    }

    public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
            HeaderType contentType, String protocol) {
        super();
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.acceptedType = acceptedType;
        this.contentType = contentType;
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public HeaderType getAcceptedType() {
        return acceptedType;
    }

    public void setAcceptedType(HeaderType acceptedType) {
        this.acceptedType = acceptedType;
    }

    public HeaderType getContentType() {
        return contentType;
    }

    public void setContentType(HeaderType contentType) {
        this.contentType = contentType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
