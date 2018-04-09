package model;

import model.converter.SupportedComponentTypeConverter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static model.Screen.COMPONENT_SCREEN_TABLE;

/**
 * Contains information about component name, type and connections, where it can be found and in which screens is
 * component references. It also contains connection to information which fields it has.
 */
@Entity
@Table(name = ComponentResource.TABLE_NAME)
public class ComponentResource extends DtoEntity {

    public static final String TABLE_NAME = "Component";
    public static final String COMPONENT_ID = "component_id";
    public static final String COMPONENT_NAME = "name";
    public static final String COMPONENT_TYPE = "type";
    public static final String FIELD_INFO_URL_PROTOCOL = "field_info_protocol";
    public static final String FIELD_INFO_URL_HOSTNAME = "field_info_hostname";
    public static final String FIELD_INFO_URL_PORT = "field_info_port";
    public static final String FIELD_INFO_URL_PARAMETERS = "field_info_parameters";

    @Id
    @Column(name = COMPONENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = COMPONENT_NAME)
    private String name;
    @Column(name = COMPONENT_TYPE)
    @Convert(converter = SupportedComponentTypeConverter.class)
    private SupportedComponentType type;
    @OneToOne
    private ComponentConnectionPack proxyConnections;

    @ManyToMany(cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = COMPONENT_SCREEN_TABLE,
            joinColumns = @JoinColumn(name = "screen_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id"))
    private List<Screen> referencedScreens;
    @ManyToOne
    @JoinColumn(name = Application.APPLICATION_ID)
    private Application application;
    @Column(name = FIELD_INFO_URL_PROTOCOL)
    private String fieldInfoUrlProtocol;
    @Column(name = FIELD_INFO_URL_HOSTNAME)
    private String fieldInfoUrlHostname;
    @Column(name = FIELD_INFO_URL_PORT)
    private int fieldInfoUrlPort;
    @Column(name = FIELD_INFO_URL_PARAMETERS)
    private String fieldInfoUrlParameters;

    public ComponentResource() {
    }

    public ComponentResource(String name, SupportedComponentType type, ComponentConnectionPack proxyConnections) {
        this.name = name;
        this.type = type;
        this.proxyConnections = proxyConnections;
    }

    /**
     * Tells component that it is referenced in specified screen.
     *
     * @param screen the screen
     */
    public void referencedByScreen(Screen screen) {
        if (referencedScreens == null) {
            referencedScreens = new ArrayList<>();
        }
        if (!referencedScreens.contains(screen)) {
            referencedScreens.add(screen);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupportedComponentType getType() {
        return type;
    }

    public void setType(SupportedComponentType type) {
        this.type = type;
    }

    public List<Screen> getReferencedScreens() {
        return referencedScreens;
    }

    public void setReferencedScreens(List<Screen> referencedScreens) {
        this.referencedScreens = referencedScreens;
    }

    public ComponentConnectionPack getProxyConnections() {
        return proxyConnections;
    }

    public void setProxyConnections(ComponentConnectionPack proxyConnections) {
        this.proxyConnections = proxyConnections;
    }

    public String getFieldInfoUrlProtocol() {
        return fieldInfoUrlProtocol;
    }

    public void setFieldInfoUrlProtocol(String fieldInfoUrlProtocol) {
        this.fieldInfoUrlProtocol = fieldInfoUrlProtocol;
    }

    public String getFieldInfoUrlHostname() {
        return fieldInfoUrlHostname;
    }

    public void setFieldInfoUrlHostname(String fieldInfoUrlHostname) {
        this.fieldInfoUrlHostname = fieldInfoUrlHostname;
    }

    public int getFieldInfoUrlPort() {
        return fieldInfoUrlPort;
    }

    public void setFieldInfoUrlPort(int fieldInfoUrlPort) {
        this.fieldInfoUrlPort = fieldInfoUrlPort;
    }

    public String getFieldInfoUrlParameters() {
        return fieldInfoUrlParameters;
    }

    public void setFieldInfoUrlParameters(String fieldInfoUrlParameters) {
        if (!fieldInfoUrlParameters.startsWith("/")) {
            fieldInfoUrlParameters = "/" + fieldInfoUrlParameters;
        }
        this.fieldInfoUrlParameters = fieldInfoUrlParameters;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
