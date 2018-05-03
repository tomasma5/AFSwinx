package model.afclassification;

import java.util.ArrayList;
import java.util.List;

/**
 * Client holds context information about application user device and so on
 */
public class Client {

    private int knowledge;

    private List<ClientProperty> clientProperties;
    private List<NearbyDevice> nearbyDevices;

    private Device device;

    private String deviceIdentifier;

    private String action;

    private String username;

    public int getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(int knowledge) {
        this.knowledge = knowledge;
    }

    public List<ClientProperty> getClientProperties() {
        return clientProperties;
    }

    public void addProperty(ClientProperty clientProperty) {
        if (this.clientProperties == null) {
            this.clientProperties = new ArrayList<>();
        }
        this.clientProperties.add(clientProperty);
    }

    public void addNearbyDevice(NearbyDevice nearbyDevice) {
        if (this.nearbyDevices == null) {
            this.nearbyDevices = new ArrayList<>();
        }
        this.nearbyDevices.add(nearbyDevice);
    }

    public void setClientProperties(List<ClientProperty> clientProperties) {
        this.clientProperties = clientProperties;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public List<NearbyDevice> getNearbyDevices() {
        return nearbyDevices;
    }

    public String getClientPropertyValue(Property key) {
        for (ClientProperty clientProperty : clientProperties) {
            if (clientProperty.getProperty().equals(key)) {
                return clientProperty.getValue();
            }
        }
        return null;
    }

    public void setNearbyDevices(List<NearbyDevice> nearbyDevices) {
        this.nearbyDevices = nearbyDevices;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Client{" +
                "knowledge=" + knowledge +
                ", clientProperties=" + clientProperties +
                ", nearbyDevices=" + nearbyDevices +
                ", device=" + device +
                ", deviceIdentifier='" + deviceIdentifier + '\'' +
                ", action='" + action + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
