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
}
