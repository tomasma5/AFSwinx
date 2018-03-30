package model.afclassification;

import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

/**
 * This structure is for representing component fields in business cases
 */
public class BCField extends MongoDocumentEntity {

	private Field field;

	private ObjectId phaseId;
	private ObjectId screenId;
	private ObjectId componentId;

	private BCFieldSeverity fieldSpecification;
	
	private String classUri;

	public BCField() {
	}

	public BCField(Field field, ObjectId phaseId, ObjectId screenId, ObjectId componentId) {
		this.field = field;
		this.phaseId = phaseId;
		this.screenId = screenId;
		this.componentId = componentId;
	}

	public ObjectId getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(ObjectId phaseId) {
		this.phaseId = phaseId;
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

	public ObjectId getScreenId() {
		return screenId;
	}

	public void setScreenId(ObjectId screenId) {
		this.screenId = screenId;
	}

	public ObjectId getComponentId() {
		return componentId;
	}

	public void setComponentId(ObjectId componentId) {
		this.componentId = componentId;
	}
}
