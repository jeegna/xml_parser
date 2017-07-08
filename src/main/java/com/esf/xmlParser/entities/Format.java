package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a Format tag in FCPXML
 *
 * @author Jeegna Patel
 * @version 2017/07/04
 * @since 1.8
 */
public class Format implements Comparable<Format>, Serializable {

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
	 * Gets the id
	 * 
	 * @return The id
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            The new id to set
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
	 * Gets the name
	 * 
	 * @return The name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            The new name to set
	 */
	public void setName(String name) {
		if (name == null) {
			name = "";
		}
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
	 * Gets the width
	 * 
	 * @return The width
	 */
	public int getWidth() {
		return width.get();
	}

	/**
	 * Sets the width
	 * 
	 * @param width
	 *            The new width to set
	 */
	public void setWidth(int width) {
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
	 * Gets the height
	 * 
	 * @return The height
	 */
	public int getHeight() {
		return height.get();
	}

	/**
	 * Sets the height
	 * 
	 * @param height
	 *            The new height to set
	 */
	public void setHeight(int height) {
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
	 * Gets the frame duration
	 * 
	 * @return The frame duration
	 */
	public String getFrameDuration() {
		return frameDuration.get();
	}

	/**
	 * Sets the frame duration
	 * 
	 * @param frameDuration
	 *            The new frame duration to set
	 */
	public void setFrameDuration(String frameDuration) {
		if (frameDuration == null) {
			frameDuration = "0fps";
		}
		this.frameDuration.set(frameDuration);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Format[id=%s, name=%s, width=%s, height=%s, frameDuration=%s]", id.get(), name.get(),
				width.get(), height.get(), frameDuration.get());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Format o) {
		if (o == null) {
			return 1;
		}

		return Integer.compare(Integer.parseInt(id.get().substring(1)), (Integer.parseInt(o.id.get().substring(1))));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Format) || id.isEmpty().get()) {
			return false;
		}

		Format f = (Format) o;

		return id.equals(f.id);
	}
}
