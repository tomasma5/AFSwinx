package model;

import java.util.List;

public class Application extends MongoDocumentEntity{

    private String uuid;
    private String applicationName;
    private String remoteProtocol;
    private String remoteHostname;
    private int remotePort;
    private String proxyProtocol;
    private String proxyHostname;
    private int proxyPort;
    private List<Screen> screenList;

    public Application() {
    }

    public Application(String uuid, String applicationName,
                       String remoteProtocol, String remoteHostname, int remotePort,
                       String proxyProtocol, String proxyHostname, int proxyPort,
                       List<Screen> screenList) {
        this.uuid = uuid;
        this.applicationName = applicationName;
        this.screenList = screenList;
        this.remoteHostname = remoteHostname;
        this.remoteProtocol = remoteProtocol;
        this.remotePort = remotePort;
        this.proxyProtocol = proxyProtocol;
        this.proxyHostname = proxyHostname;
        this.proxyPort = proxyPort;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<Screen> getScreenList() {
        return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
        this.screenList = screenList;
    }

    public String getRemoteProtocol() {
        return remoteProtocol;
    }

    public void setRemoteProtocol(String remoteProtocol) {
        this.remoteProtocol = remoteProtocol;
    }

    public String getRemoteHostname() {
        return remoteHostname;
    }

    public void setRemoteHostname(String remoteHostname) {
        this.remoteHostname = remoteHostname;
    }

    public String getProxyProtocol() {
        return proxyProtocol;
    }

    public void setProxyProtocol(String proxyProtocol) {
        this.proxyProtocol = proxyProtocol;
    }

    public String getProxyHostname() {
        return proxyHostname;
    }

    public void setProxyHostname(String proxyHostname) {
        this.proxyHostname = proxyHostname;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
