package model.afclassification;

/**
 * This structure is generated after field scoring and classifications, therefore it represents final field form
 *
 */
public class GeneratedField {
	
	private BCField bcField;
	
	private Behavior behavior;

	private String value;
	
	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public BCField getBcField() {
		return bcField;
	}

	public void setBcField(BCField bcField) {
		this.bcField = bcField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
