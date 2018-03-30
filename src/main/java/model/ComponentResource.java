package model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about component name, type and connections, where it can be found and in which screens is
 * component references. It also contains connection to information which fields it has.
 */
public class ComponentResource extends MongoDocumentEntity {

    private String name;
    private SupportedComponentType type;
    private ComponentConnectionPack proxyConnections;
    private List<ObjectId> referencedScreensIds;

    private String fieldInfoUrlProtocol;
    private String fieldInfoUrlHostname;
    private int fieldInfoUrlPort;
    private String fieldInfoUrlParameters;

    private ObjectId applicationId;

    public ComponentResource() {
    }

    public ComponentResource(String name, SupportedComponentType type, ComponentConnectionPack proxyConnections,
                             ComponentConnectionPack realConnections, ObjectId applicationId) {
        this.name = name;
        this.type = type;
        this.proxyConnections = proxyConnections;
        this.applicationId = applicationId;
    }

    /**
     * Tells component that it is referenced in specified screen.
     *
     * @param screen the screen
     */
    public void referencedByScreen(Screen screen){
        if(referencedScreensIds == null){
            referencedScreensIds = new ArrayList<>();
        }
        if(!referencedScreensIds.contains(screen.getId())) {
            referencedScreensIds.add(screen.getId());
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

    public List<ObjectId> getReferencedScreensIds() {
        return referencedScreensIds;
    }

    public void setReferencedScreensIds(List<ObjectId> referencedScreensIds) {
        this.referencedScreensIds = referencedScreensIds;
    }

    public ComponentConnectionPack getProxyConnections() {
        return proxyConnections;
    }

    public void setProxyConnections(ComponentConnectionPack proxyConnections) {
        this.proxyConnections = proxyConnections;
    }

    public ObjectId getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ObjectId applicationId) {
        this.applicationId = applicationId;
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
        if(!fieldInfoUrlParameters.startsWith("/")){
            fieldInfoUrlParameters = "/" + fieldInfoUrlParameters;
        }
        this.fieldInfoUrlParameters = fieldInfoUrlParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentResource that = (ComponentResource) o;

        if (!name.equals(that.name)) return false;
        if (type != that.type) return false;
        return applicationId.equals(that.applicationId);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + applicationId.hashCode();
        return result;
    }


}
