package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Clip implements Serializable {

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private StringProperty name;
	private StringProperty offset;
	private StringProperty duration;
	private StringProperty start;
	private StringProperty tcFormat;

	public Clip() {
		id = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
		offset = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		start = new SimpleStringProperty();
		tcFormat = new SimpleStringProperty();
	}

	/**
	 * Gets the id property
	 *
	 * @return The id property
	 */
	public IntegerProperty idProperty() {
		return id;
	}

	/**
	 * Gets the id
	 *
	 * @return The id
	 */
	public int getId() {
		return id.get();
	}

	/**
	 * Sets the id
	 *
	 * @param id
	 *            The new id to set
	 */
	public void setId(int id) {
		this.id.set(id);
	}

	/**
	 * Gets the duration property
	 *
	 * @return The duration property
	 */
	public StringProperty durationProperty() {
		return duration;
	}

	/**
	 * Gets the duration
	 *
	 * @return The duration
	 */
	public String getDuration() {
		return duration.get();
	}

	/**
	 * Sets the duration
	 *
	 * @param duration
	 *            The new duration to set
	 */
	public void setDuration(String duration) {
		this.duration.set(duration);
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
		this.name.set(name);
	}

	/**
	 * Gets the start property
	 *
	 * @return The start property
	 */
	public StringProperty startProperty() {
		return start;
	}

	/**
	 * Gets the start
	 *
	 * @return The start
	 */
	public String getStart() {
		return start.get();
	}

	/**
	 * Sets the start
	 *
	 * @param start
	 *            The new start to set
	 */
	public void setStart(String start) {
		this.start.set(start);
	}

	/**
	 * Gets the offset property
	 *
	 * @return The offset property
	 */
	public StringProperty offsetProperty() {
		return offset;
	}

	/**
	 * Gets the offset
	 *
	 * @return The offset
	 */
	public String getOffset() {
		return offset.get();
	}

	/**
	 * Sets the offset
	 *
	 * @param offset
	 *            The new offset to set
	 */
	public void setOffset(String offset) {
		this.offset.set(offset);
	}

	/**
	 * Gets the tcFormat property
	 *
	 * @return The tcFormat property
	 */
	public StringProperty tcFormatProperty() {
		return tcFormat;
	}

	/**
	 * Gets the tcFormat
	 *
	 * @return The tcFormat
	 */
	public String getTcFormat() {
		return tcFormat.get();
	}

	/**
	 * Sets the tcFormat
	 *
	 * @param tcFormat
	 *            The new tcFormat to set
	 */
	public void setTcFormat(String tcFormat) {
		this.tcFormat.set(tcFormat);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Clip[name=%s, offset=%s, duration=%s, start=%s, tcFormat=%s]", name, offset, duration,
				start, tcFormat);
	}
}
