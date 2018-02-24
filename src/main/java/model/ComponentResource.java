package model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ComponentResource extends MongoDocumentEntity {

    private String name;
    private SupportedComponentType type;
    private ComponentConnectionPack connections;
    private List<ObjectId> referencedScreensIds;
    private ObjectId applicationId;

    public ComponentResource() {
    }

    public ComponentResource(String name, SupportedComponentType type, ComponentConnectionPack connections, ObjectId applicationId) {
        this.name = name;
        this.type = type;
        this.connections = connections;
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

    public ComponentConnectionPack getConnections() {
        return connections;
    }

    public void setConnections(ComponentConnectionPack connections) {
        this.connections = connections;
    }

    public List<ObjectId> getReferencedScreensIds() {
        return referencedScreensIds;
    }

    public void setReferencedScreensIds(List<ObjectId> referencedScreensIds) {
        this.referencedScreensIds = referencedScreensIds;
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
