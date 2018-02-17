package model;

public class ComponentResource extends MongoDocumentEntity {

    private String name;
    private SupportedComponentType type;
    private ComponentConnectionPack connections;

    public ComponentResource() {
    }

    public ComponentResource(String name, SupportedComponentType type, ComponentConnectionPack connections) {
        this.name = name;
        this.type = type;
        this.connections = connections;
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
}
