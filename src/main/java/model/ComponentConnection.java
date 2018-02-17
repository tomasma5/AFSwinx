package model;

import java.util.Map;

public class ComponentConnection {

    private String address;
    private int port;
    private String parameters;
    private String protocol;
    private Map<String, String> headerParams;
    private Map<String, String> securityParams;

    public ComponentConnection() {
    }

    public ComponentConnection(String address, int port, String parameters, String protocol, Map<String, String> headerParams, Map<String, String> securityParams) {
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.protocol = protocol;
        this.headerParams = headerParams;
        this.securityParams = securityParams;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public Map<String, String> getSecurityParams() {
        return securityParams;
    }

    public void setSecurityParams(Map<String, String> securityParams) {
        this.securityParams = securityParams;
    }
}
