package model.afclassification;

import java.util.List;

public class Field {
	
	private String className;
	
	private String fieldName;
	
	private String type;
	
	private String value;
	
	private List<BCField> BCFields;

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

}
