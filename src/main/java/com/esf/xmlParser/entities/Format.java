package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a Format tag in FCPXML.
 *
 * @author Jeegna Patel
 * @version 2017/07/04
 * @since 1.8
 */
public class Format extends Element implements Comparable<Format>, Serializable {

	private static final long serialVersionUID = 1L;

	private StringProperty id;
	private IntegerProperty width;
	private IntegerProperty height;
	private StringProperty frameDuration;

	/**
	 * Instantiates a new format entity.
	 */
	public Format() {
		id = new SimpleStringProperty();
		width = new SimpleIntegerProperty();
		height = new SimpleIntegerProperty();
		frameDuration = new SimpleStringProperty();
	}

	/**
	 * Gets the id property.
	 *
	 * @return The id property.
	 */
	public StringProperty idProperty() {
		return id;
	}

	/**
	 * Gets the id.
	 * 
	 * @return The id.
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            The new id to set.
	 */
	public void setId(String id) {
		this.id.set(id);
	}

	/**
	 * Gets the width property.
	 *
	 * @return The width property.
	 */
	public IntegerProperty widthProperty() {
		return width;
	}

	/**
	 * Gets the width.
	 * 
	 * @return The width.
	 */
	public int getWidth() {
		return width.get();
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            The new width to set.
	 */
	public void setWidth(int width) {
		this.width.set(width);
	}

	/**
	 * Gets the height property.
	 *
	 * @return The height property.
	 */
	public IntegerProperty heightProperty() {
		return height;
	}

	/**
	 * Gets the height.
	 * 
	 * @return The height.
	 */
	public int getHeight() {
		return height.get();
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            The new height to set.
	 */
	public void setHeight(int height) {
		this.height.set(height);
	}

	/**
	 * Gets the frame duration property.
	 *
	 * @return The frame duration property.
	 */
	public StringProperty frameDurationProperty() {
		return frameDuration;
	}

	/**
	 * Gets the frame duration.
	 * 
	 * @return The frame duration.
	 */
	public String getFrameDuration() {
		return frameDuration.get();
	}

	/**
	 * Sets the frame duration.
	 * 
	 * @param frameDuration
	 *            The new frame duration to set.
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
		return String.format(getClass().getSimpleName() + " [id=%s, name=%s, width=%s, height=%s, frameDuration=%s]",
				getId(), getName(), getWidth(), getHeight(), getFrameDuration());
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

		return Integer.compare(Integer.parseInt(getId().substring(1)), (Integer.parseInt(o.getId().substring(1))));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Format)) {
			return false;
		}

		Format format = (Format) o;

		return getId().equals(format.getId());
	}
}
