package model;

import java.net.URL;
import java.util.List;

public class Application extends MongoDocumentEntity{

    private String uuid;
    private String applicationName;
    private String remoteUrl;
    private int remotePort;
    private List<Screen> screenList;

    public Application() {
    }

    public Application(String uuid, String applicationName, String remoteUrl, int remotePort, List<Screen> screenList) {
        this.uuid = uuid;
        this.applicationName = applicationName;
        this.screenList = screenList;
        this.remoteUrl = remoteUrl;
        this.remotePort = remotePort;
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

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
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
