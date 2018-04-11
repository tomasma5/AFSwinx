package model.afclassification;

/**
 * Represents addiitional client information
 */
public class ClientProperty {

    public ClientProperty() {
    }

    public ClientProperty(Property property, String value) {
		this.value = value;
		this.property = property;
	}

	private String value;
	
	private Property property;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
