package model;

import java.util.Map;

public class ComponentConnection {

    private String address;
    private int port;
    private String parameters;
    private String protocol;
    private String realAddress;
    private int realPort;
    private String realParameters;
    private String realProtocol;
    private Map<String, String> headerParams;
    private Map<String, String> securityParams;

    public ComponentConnection() {
    }

    public ComponentConnection(String address, int port, String parameters, String protocol,
                               String realAddress, int realPort, String realParameters, String realProtocol,
                               Map<String, String> headerParams, Map<String, String> securityParams) {
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.protocol = protocol;
        this.realAddress = realAddress;
        this.realPort = realPort;
        this.realParameters = realParameters;
        this.realProtocol = realProtocol;
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

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public int getRealPort() {
        return realPort;
    }

    public void setRealPort(int realPort) {
        this.realPort = realPort;
    }

    public String getRealParameters() {
        return realParameters;
    }

    public void setRealParameters(String realParameters) {
        this.realParameters = realParameters;
    }

    public String getRealProtocol() {
        return realProtocol;
    }

    public void setRealProtocol(String realProtocol) {
        this.realProtocol = realProtocol;
    }
}
