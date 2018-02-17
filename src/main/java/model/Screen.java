package model;


import java.util.List;

public class Screen extends MongoDocumentEntity {

    private String heading;
    private String screenUrl;
    private List<ComponentResource> components;

    public Screen() {
    }

    public Screen(String heading, String screenUrl, List<ComponentResource> components) {
        this.heading = heading;
        this.screenUrl = screenUrl;
        this.components = components;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public List<ComponentResource> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentResource> components) {
        this.components = components;
    }
}
