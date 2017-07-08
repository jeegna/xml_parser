package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Audio in FCPXML
 *
 * @author Jeegna Patel
 * @version 2017/04/15
 * @since 1.8
 */
public class Audio implements Comparable<Audio>, Serializable {

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private Asset asset;
	private IntegerProperty lane;
	private StringProperty role;
	private StringProperty offset;
	private StringProperty duration;
	private StringProperty start;
	private StringProperty srcCh;
	private IntegerProperty srcID;

	public Audio() {
		id = new SimpleIntegerProperty();
		asset = new Asset();
		lane = new SimpleIntegerProperty();
		role = new SimpleStringProperty();
		offset = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		start = new SimpleStringProperty();
		srcCh = new SimpleStringProperty();
		srcID = new SimpleIntegerProperty();
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
		if (duration == null) {
			duration = "0s";
		}
		this.duration.set(duration);
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
		if (start == null) {
			start = "0s";
		}
		this.start.set(start);
	}

	/**
	 * Gets the role property
	 *
	 * @return The role property
	 */
	public StringProperty roleProperty() {
		return role;
	}

	/**
	 * Gets the role
	 *
	 * @return The role
	 */
	public String getRole() {
		return role.get();
	}

	/**
	 * Sets the role
	 *
	 * @param role
	 *            The new role to set
	 */
	public void setRole(String role) {
		if (role == null) {
			role = "";
		}
		this.role.set(role);
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
		if (offset == null) {
			offset = "0s";
		}
		this.offset.set(offset);
	}

	/**
	 * Gets the asset
	 *
	 * @return The asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Sets the asset
	 *
	 * @param asset
	 *            The new asset to set
	 */
	public void setAsset(Asset asset) {
		if (asset == null) {
			asset = new Asset();
		}
		this.asset = asset;
	}

	/**
	 * Gets the srcCh property
	 *
	 * @return The srcCh property
	 */
	public StringProperty srcChProperty() {
		return srcCh;
	}

	/**
	 * Gets the srcCh
	 *
	 * @return The srcCh
	 */
	public String getSrcCh() {
		return srcCh.get();
	}

	/**
	 * Sets the srcCh
	 *
	 * @param srcCh
	 *            The new srcCh to set
	 */
	public void setSrcCh(String srcCh) {
		if (srcCh == null) {
			srcCh = "";
		}
		this.srcCh.set(srcCh);
	}

	/**
	 * Gets the srcID property
	 *
	 * @return The srcID property
	 */
	public IntegerProperty srcIdProperty() {
		return srcID;
	}

	/**
	 * Gets the srcID
	 *
	 * @return The srcID
	 */
	public int getSrcId() {
		return srcID.get();
	}

	/**
	 * Sets the srcID
	 *
	 * @param srcID
	 *            The new srcID to set
	 */
	public void setSrcId(int srcID) {
		this.srcID.set(srcID);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Video[ref=%s, lane=%s, duration=%s, start=%s, role=%s, offset=%s, srcCh=%s, srcId=%s]",
				asset, lane.get(), duration.get(), start.get(), role.get(), offset.get(), srcCh.get(), srcID.get());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Audio o) {
		if (o == null) {
			return 1;
		}

		return Integer.compare(Integer.parseInt(asset.getId().substring(1)),
				(Integer.parseInt(o.asset.getId().substring(1))));
	}
}
