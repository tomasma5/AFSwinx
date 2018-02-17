package model;

import java.util.List;

public class Application extends MongoDocumentEntity{

    private String applicationName;
    private List<Screen> screenList;

    public Application() {
    }

    public Application(String applicationName, List<Screen> screenList) {
        this.applicationName = applicationName;
        this.screenList = screenList;
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
}
