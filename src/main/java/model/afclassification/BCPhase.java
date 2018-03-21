package model.afclassification;

import model.MongoDocumentEntity;
import model.Screen;

import java.util.ArrayList;
import java.util.List;

public class BCPhase extends MongoDocumentEntity {

	private BusinessCase businessCase;

	private List<BCField> fields;

	private List<Screen> linkedScreens;

	private ConfigurationPack configuration;
	
	private String name;
	
	public synchronized void addBCField(BCField field){
		if(this.fields == null){
			this.fields = new ArrayList<>();
		}
		this.fields.add(field);
	}

	public synchronized void addLinkedScreen(Screen screen) {
		if(this.linkedScreens == null){
			this.linkedScreens = new ArrayList<>();
		}
		this.linkedScreens.add(screen);
	}

	public BusinessCase getBusinessCase() {
		return businessCase;
	}

	public void setBusinessCase(BusinessCase businessCase) {
		this.businessCase = businessCase;
	}

	public List<BCField> getFields() {
		return fields;
	}

	public void setFields(List<BCField> fields) {
		this.fields = fields;
	}

	public ConfigurationPack getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigurationPack configuration) {
		this.configuration = configuration;
	}

	public List<Screen> getLinkedScreens() {
		return linkedScreens;
	}

	public void setLinkedScreens(List<Screen> linkedScreens) {
		this.linkedScreens = linkedScreens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
