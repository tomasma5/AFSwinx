package model;

public class ComponentConnectionPack {

    private ComponentConnection modelConnection;
    private ComponentConnection dataConnection;
    private ComponentConnection sendConnection;

    public ComponentConnectionPack() {
    }

    public ComponentConnectionPack(ComponentConnection modelConnection, ComponentConnection dataConnection, ComponentConnection sendConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
    }

    public ComponentConnection getModelConnection() {
        return modelConnection;
    }

    public void setModelConnection(ComponentConnection modelConnection) {
        this.modelConnection = modelConnection;
    }

    public ComponentConnection getDataConnection() {
        return dataConnection;
    }

    public void setDataConnection(ComponentConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    public ComponentConnection getSendConnection() {
        return sendConnection;
    }

    public void setSendConnection(ComponentConnection sendConnection) {
        this.sendConnection = sendConnection;
    }
}
