package com.tomscz.afclassification.model;

import java.util.List;

public class BCPhase {

	private BusinessCase businessCase;

	private List<BCField> fields;

	private List<Configuration> configurations;

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

	public List<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Configuration> configurations) {
		this.configurations = configurations;
	}

}
