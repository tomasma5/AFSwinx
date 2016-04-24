package com.tomscz.afclassification.model;

import java.util.List;

public class Client {
	
	private int knowledge;

	private List<ClientProperty> clientProperties;
	
	private Device device;
	
	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public List<ClientProperty> getClientProperties() {
		return clientProperties;
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

}
