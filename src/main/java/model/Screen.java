package model;


import model.afclassification.BCPhase;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen model. Contains information about screen in client application, which components it has inside and on which
 * endpoint can it be found on proxy.
 */
@Entity
@Table(name = Screen.TABLE_NAME)
public class Screen extends DtoEntity {

    public static final String TABLE_NAME = "Screen";
    public static final String SCREEN_ID = "screen_id";
    public static final String SCREEN_KEY = "key";
    public static final String SCREEN_NAME = "name";
    public static final String SCREEN_URL = "url";
    public static final String MENU_ORDER = "menu_order";

    public static final String COMPONENT_SCREEN_TABLE = "component_screen";

    @Id
    @Column(name = SCREEN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = SCREEN_KEY)
    private String key;
    @Column(name = SCREEN_NAME)
    private String name;
    @Column(name = SCREEN_URL)
    private String screenUrl;
    @Column(name = MENU_ORDER)
    private int menuOrder;
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = COMPONENT_SCREEN_TABLE,
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "screen_id"))
    private List<ComponentResource> components;
    @ManyToOne
    @JoinColumn(name = Application.APPLICATION_ID)
    private Application application;

    @ManyToOne
    @JoinColumn(name = BCPhase.PHASE_ID)
    private BCPhase phase;

    public Screen() {
    }

    public Screen(String key, String name, String screenUrl, int menuOrder, List<ComponentResource> components,
                  Application application, BCPhase phase) {
        this.key = key;
        this.name = name;
        this.screenUrl = screenUrl;
        this.menuOrder = menuOrder;
        this.components = components;
        this.application = application;
        this.phase = phase;
    }

    /**
     * Adds component resource to screen.
     *
     * @param componentResource the component resource
     */
    public void addComponentResource(ComponentResource componentResource) {
        if (components == null) {
            components = new ArrayList<>();
        }
        if (!components.contains(componentResource)) {
            components.add(componentResource);
        }
    }

    /**
     * Remove component resource from screen.
     *
     * @param componentResource the component resource
     */
    public void removeComponentResource(ComponentResource componentResource) {
        if (components == null) {
            return;
        }
        components.remove(componentResource);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BCPhase getPhase() {
        return phase;
    }

    public void setPhase(BCPhase phase) {
        this.phase = phase;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screen screen = (Screen) o;

        if (menuOrder != screen.menuOrder) return false;
        if (id != null ? !id.equals(screen.id) : screen.id != null) return false;
        if (key != null ? !key.equals(screen.key) : screen.key != null) return false;
        if (name != null ? !name.equals(screen.name) : screen.name != null) return false;
        return screenUrl != null ? screenUrl.equals(screen.screenUrl) : screen.screenUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (screenUrl != null ? screenUrl.hashCode() : 0);
        result = 31 * result + menuOrder;
        return result;
    }
}
