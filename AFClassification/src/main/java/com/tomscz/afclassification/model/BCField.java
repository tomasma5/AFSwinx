package com.tomscz.afclassification.model;

public class BCField {

	private Field field;

	private BCPhase phase;
	
	private String classUri;

	public BCField(Field field, BCPhase phase) {
		this.field = field;
		this.phase = phase;
	}

	public BCPhase getPhase() {
		return phase;
	}

	public void setPhase(BCPhase phase) {
		this.phase = phase;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getClassUri() {
		return classUri;
	}

	public void setClassUri(String classUri) {
		this.classUri = classUri;
	}

}
