package model.afclassification;

import java.util.ArrayList;
import java.util.List;

public class Client {
	
	private int knowledge;

	private List<ClientProperty> clientProperties;
	
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
		if(this.clientProperties == null){
			this.clientProperties = new ArrayList<ClientProperty>();
		}
		this.clientProperties.add(clientProperty);
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
}
