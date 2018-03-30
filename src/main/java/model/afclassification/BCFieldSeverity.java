package model.afclassification;

/**
 * Business case field severity - it contains how important the field is
 */
public class BCFieldSeverity {

	private Severity severity;
	
	private Purpose purpose;
	
	private Double score;
	
	private BCField field;

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public BCField getField() {
		return field;
	}

	public void setField(BCField field) {
		this.field = field;
	}
	
}
