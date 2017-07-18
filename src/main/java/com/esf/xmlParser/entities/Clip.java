package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Clip tag in FCPXML.
 *
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class Clip extends Element implements Serializable {

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private StringProperty offset;
	private StringProperty duration;
	private StringProperty start;
	private StringProperty tcFormat;

	/**
	 * Instantiates a new clip entity.
	 */
	public Clip() {
		id = new SimpleIntegerProperty();
		offset = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		start = new SimpleStringProperty();
		tcFormat = new SimpleStringProperty();
	}

	/**
	 * Gets the id property.
	 *
	 * @return The id property.
	 */
	public IntegerProperty idProperty() {
		return id;
	}

	/**
	 * Gets the id.
	 *
	 * @return The id.
	 */
	public int getId() {
		return id.get();
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            The new id to set.
	 */
	public void setId(int id) {
		this.id.set(id);
	}

	/**
	 * Gets the duration property.
	 *
	 * @return The duration property.
	 */
	public StringProperty durationProperty() {
		return duration;
	}

	/**
	 * Gets the duration.
	 *
	 * @return The duration.
	 */
	public String getDuration() {
		return duration.get();
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration
	 *            The new duration to set.
	 */
	public void setDuration(String duration) {
		if (duration == null) {
			duration = "0s";
		}
		this.duration.set(duration);
	}

	/**
	 * Gets the start property.
	 *
	 * @return The start property.
	 */
	public StringProperty startProperty() {
		return start;
	}

	/**
	 * Gets the start.
	 *
	 * @return The start.
	 */
	public String getStart() {
		return start.get();
	}

	/**
	 * Sets the start.
	 *
	 * @param start
	 *            The new start to set.
	 */
	public void setStart(String start) {
		if (start == null) {
			start = "0s";
		}
		this.start.set(start);
	}

	/**
	 * Gets the offset property.
	 *
	 * @return The offset property.
	 */
	public StringProperty offsetProperty() {
		return offset;
	}

	/**
	 * Gets the offset.
	 *
	 * @return The offset.
	 */
	public String getOffset() {
		return offset.get();
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset
	 *            The new offset to set.
	 */
	public void setOffset(String offset) {
		if (offset == null) {
			offset = "0s";
		}
		this.offset.set(offset);
	}

	/**
	 * Gets the TC format property.
	 *
	 * @return The TC format property.
	 */
	public StringProperty tcFormatProperty() {
		return tcFormat;
	}

	/**
	 * Gets the TC format.
	 *
	 * @return The TC format.
	 */
	public String getTcFormat() {
		return tcFormat.get();
	}

	/**
	 * Sets the TC format.
	 *
	 * @param tcFormat
	 *            The new TC format to set.
	 */
	public void setTcFormat(String tcFormat) {
		if (tcFormat == null) {
			tcFormat = "";
		}
		this.tcFormat.set(tcFormat);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format(getClass().getSimpleName() + " [name=%s, offset=%s, duration=%s, start=%s, tcFormat=%s]",
				getName(), getOffset(), getDuration(), getStart(), getTcFormat());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Clip)) {
			return false;
		}

		Clip clip = (Clip) o;
		return getId() == clip.getId();
	}
}
