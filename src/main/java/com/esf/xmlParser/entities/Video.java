package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a Video in FCPXML
 *
 * @author Jeegna Patel
 * @version 2017/04/15
 * @since 1.8
 */
public class Video implements Comparable<Video>, Serializable {

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private StringProperty name;
	private IntegerProperty lane;
	private StringProperty offset;
	private Asset asset;
	private StringProperty duration;
	private StringProperty start;

	public Video() {
		id = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
		lane = new SimpleIntegerProperty();
		offset = new SimpleStringProperty();
		asset = new Asset();
		duration = new SimpleStringProperty();
		start = new SimpleStringProperty();
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
	 * Gets the lane property
	 *
	 * @return The lane property
	 */
	public IntegerProperty laneProperty() {
		return lane;
	}

	/**
	 * Gets the lane
	 *
	 * @return The lane
	 */
	public int getLane() {
		return lane.get();
	}

	/**
	 * Sets the lane
	 *
	 * @param lane
	 *            The new lane to set
	 */
	public void setLane(int lane) {
		this.lane.set(lane);
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
	 * Gets the Asset
	 *
	 * @return The Asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Sets the Asset
	 *
	 * @param asset
	 *            The new Asset to set
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Video[ref=%s, duration=%s, name=%s, start=%s, lane=%s, offset=%s]", asset, duration.get(),
				name.get(), start.get(), lane.get(), offset.get());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Video o) {
		if (o == null) {
			return 1;
		}

		return Integer.compare(Integer.parseInt(asset.getId().substring(1)),
				(Integer.parseInt(o.asset.getId().substring(1))));
	}
}
