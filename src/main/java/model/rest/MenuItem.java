package model.rest;

public class MenuItem {

    private String key;
    private String displayText;
    private String url;
    private int menuOrder;

    public MenuItem() {
    }

    public MenuItem(String key, String displayText, String url, int menuOrder) {
        this.key = key;
        this.displayText = displayText;
        this.url = url;
        this.menuOrder = menuOrder;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
