package com.tomscz.afclassification.model;

import java.util.List;

public class BusinessCase {

	private String name;

	private String description;

	private List<BCPhase> phases;

	public BusinessCase() {

	}

	public BusinessCase(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public List<BCPhase> getPhases() {
		return phases;
	}

	public void setPhases(List<BCPhase> phases) {
		this.phases = phases;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
