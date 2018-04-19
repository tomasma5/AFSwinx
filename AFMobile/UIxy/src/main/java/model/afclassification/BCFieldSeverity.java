package model.afclassification;

import model.DtoEntity;
import model.converter.PurposeConverter;
import model.converter.SeverityConverter;

import javax.persistence.*;

/**
 * Business case field severity - it contains how important the field is
 */
@Entity
@Table(name = BCFieldSeverity.TABLE_NAME)
public class BCFieldSeverity extends DtoEntity {

	public static final String TABLE_NAME = "BCFieldSeverity";
	public static final String BC_FIELD_SEVERITY_ID = "field_severity_id";
	public static final String SEVERITY = "severity";
	public static final String PURPOSE = "purpose";
	public static final String SCORE = "score";

	@Id
	@Column(name = BC_FIELD_SEVERITY_ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = SEVERITY)
	@Convert(converter = SeverityConverter.class)
	private Severity severity;

	@Column(name = PURPOSE)
	@Convert(converter = PurposeConverter.class)
	private Purpose purpose;

	@Column(name = SCORE)
	private Double score;

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

	@Override
	public Integer getId() {
		return id;
	}
}
