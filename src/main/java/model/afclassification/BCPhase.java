package model.afclassification;

import java.util.ArrayList;
import java.util.List;

public class BCPhase {

	private BusinessCase businessCase;

	private List<BCField> fields;

	private List<Configuration> configurations;
	
	private String name;
	
	public synchronized void addBCField(BCField field){
		if(this.fields == null){
			this.fields = new ArrayList<>();
		}
		this.fields.add(field);
	}

	public BusinessCase getBusinessCase() {
		return businessCase;
	}

	public void setBusinessCase(BusinessCase businessCase, String name) {
		this.businessCase = businessCase;
		this.setName(name);
	}

	public List<BCField> getFields() {
		return fields;
	}

	public void setFields(List<BCField> fields) {
		this.fields = fields;
	}

	public List<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Configuration> configurations) {
		this.configurations = configurations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
