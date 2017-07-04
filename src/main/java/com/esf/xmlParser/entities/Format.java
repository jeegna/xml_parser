package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Format implements Serializable {

	private static final long serialVersionUID = 1L;

	private StringProperty id;
	private StringProperty name;
	private IntegerProperty width;
	private IntegerProperty height;
	private StringProperty frameDuration;

	public Format() {
		id = new SimpleStringProperty();
		name = new SimpleStringProperty();
		width = new SimpleIntegerProperty();
		height = new SimpleIntegerProperty();
		frameDuration = new SimpleStringProperty();
	}

	/**
	 * Gets the id property
	 *
	 * @return The id property
	 */
	public StringProperty idProperty() {
		return id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id.set(id);
	}

	/**
	 * Gets the name property
	 *
	 * @return The name property
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * Gets the width property
	 *
	 * @return The width property
	 */
	public IntegerProperty widthProperty() {
		return width;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width.get();
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width.set(width);
	}

	/**
	 * Gets the height property
	 *
	 * @return The height property
	 */
	public IntegerProperty heightProperty() {
		return height;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height.get();
	}

	/**
	 * @param height
	 *            The height to set
	 */
	public void setHeight(Integer height) {
		this.height.set(height);
	}

	/**
	 * Gets the frame duration property
	 *
	 * @return The frame duration property
	 */
	public StringProperty frameDurationProperty() {
		return frameDuration;
	}

	/**
	 * @return The frame duration
	 */
	public String getFrameDuration() {
		return frameDuration.get();
	}

	/**
	 * @param frameDuration
	 *            The frame duration to set
	 */
	public void setFrameDuration(String frameDuration) {
		this.frameDuration.set(frameDuration);
	}
}
