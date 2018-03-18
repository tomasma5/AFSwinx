package model.afclassification;

public class BCField {

	private Field field;

	private BCPhase phase;
	
	private BCFieldSeverity fieldSpecification;
	
	private String classUri;

	public BCField(Field field, BCPhase phase) {
		this.field = field;
		this.phase = phase;
	}
	
	public BCField(Field field, BCPhase phase, String classUri) {
		this.field = field;
		this.phase = phase;
		this.classUri = classUri;
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

	public BCFieldSeverity getFieldSpecification() {
		return fieldSpecification;
	}

	public void setFieldSpecification(BCFieldSeverity fieldSpecification) {
		this.fieldSpecification = fieldSpecification;
	}

}
