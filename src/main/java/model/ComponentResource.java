package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ComponentResource extends MongoDocumentEntity {

    private String name;
    private SupportedComponentType type;
    private ComponentConnectionPack proxyConnections;
    @JsonIgnore
    private ComponentConnectionPack realConnections;
    private List<ObjectId> referencedScreensIds;
    private ObjectId applicationId;

    public ComponentResource() {
    }

    public ComponentResource(String name, SupportedComponentType type, ComponentConnectionPack proxyConnections,
                             ComponentConnectionPack realConnections, ObjectId applicationId) {
        this.name = name;
        this.type = type;
        this.proxyConnections = proxyConnections;
        this.realConnections = realConnections;
        this.applicationId = applicationId;
    }

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

    public ComponentConnectionPack getRealConnections() {
        return realConnections;
    }

    public void setRealConnections(ComponentConnectionPack realConnections) {
        this.realConnections = realConnections;
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
