package rest.model;

public class MenuItem {

    private String title;
    private String url;
    private int menuOrder;

    public MenuItem() {
    }

    public MenuItem(String title, String url, int menuOrder) {
        this.title = title;
        this.url = url;
        this.menuOrder = menuOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
