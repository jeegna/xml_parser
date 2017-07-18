package com.esf.xmlParser.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Element class. This class provides common attributes for all entities.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class Element {

	private StringProperty name;

	/**
	 * Instantiates a new element entity.
	 */
	public Element() {
		name = new SimpleStringProperty();
	}

	/**
	 * Gets the name property.
	 *
	 * @return The name property.
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * Gets the name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            The new name to set.
	 */
	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		this.name.set(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format(getClass().getSimpleName() + " [name=%s]", getName());
	}
}
