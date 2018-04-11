package model.afclassification;

import model.DtoEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents field of component
 */
@Entity
@Table(name = Field.TABLE_NAME)
public class Field extends DtoEntity {

	public static final String TABLE_NAME = "Field";
	public static final String FIELD_ID = "field_id";
	public static final String CLASS_NAME = "class_name";
	public static final String FIELD_NAME = "field_name";
	public static final String FIELD_TYPE = "field_type";
	public static final String VALUE = "value";

	@Id
	@Column(name = FIELD_ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = CLASS_NAME)
	private String className;

	@Column(name = FIELD_NAME)
	private String fieldName;

	@Column(name = FIELD_TYPE)
	private String type;

	@Column(name = VALUE)
	private String value;

	@OneToMany(mappedBy = "field")
	private List<BCField> BCFields;

	public void addBCField(BCField bcField){
		if(BCFields == null){
			BCFields = new ArrayList<>();
		}
		BCFields.add(bcField);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<BCField> getBCFields() {
		return BCFields;
	}

	public void setBCFields(List<BCField> bCFields) {
		BCFields = bCFields;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
