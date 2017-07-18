package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an AssetClip in FCPXML.
 *
 * @author Jeegna Patel
 * @version 2017/04/15
 * @since 1.8
 */
public class AssetClip extends Element implements Comparable<AssetClip>, Serializable {

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private Asset asset;
	private IntegerProperty lane;
	private StringProperty offset;
	private StringProperty duration;
	private StringProperty start;
	private StringProperty role;
	private Format format;
	private StringProperty tcFormat;

	/**
	 * Instantiates a new asset clip entity.
	 */
	public AssetClip() {
		id = new SimpleIntegerProperty();
		asset = new Asset();
		lane = new SimpleIntegerProperty();
		offset = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		start = new SimpleStringProperty();
		role = new SimpleStringProperty();
		format = new Format();
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
	 * Gets the Asset.
	 *
	 * @return The Asset.
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Sets the Asset.
	 *
	 * @param asset
	 *            The new Asset to set.
	 */
	public void setAsset(Asset asset) {
		if (asset == null) {
			asset = new Asset();
		}
		this.asset = asset;
	}

	/**
	 * Gets the lane property.
	 *
	 * @return The lane property.
	 */
	public IntegerProperty laneProperty() {
		return lane;
	}

	/**
	 * Gets the lane.
	 *
	 * @return The lane.
	 */
	public int getLane() {
		return lane.get();
	}

	/**
	 * Sets the lane.
	 *
	 * @param lane
	 *            The new lane to set.
	 */
	public void setLane(int lane) {
		this.lane.set(lane);
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
	 * Gets the role property.
	 *
	 * @return The role property.
	 */
	public StringProperty roleProperty() {
		return role;
	}

	/**
	 * Gets the role.
	 *
	 * @return The role.
	 */
	public String getRole() {
		return role.get();
	}

	/**
	 * Sets the role.
	 *
	 * @param role
	 *            The new role to set.
	 */
	public void setRole(String role) {
		if (role == null) {
			role = "";
		}
		this.role.set(role);
	}

	/**
	 * Gets the Format.
	 *
	 * @return The Format.
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * Sets the Format.
	 *
	 * @param format
	 *            The new Format to set.
	 */
	public void setFormat(Format format) {
		if (format == null) {
			format = new Format();
		}
		this.format = format;
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
		return String.format(
				getClass().getSimpleName()
						+ " [asset=%s, name=%s, lane=%s, duration=%s, start=%s, role=%s, offset=%s, format=%s, tcFormat=%s]",
				getAsset(), getName(), getLane(), getDuration(), getStart(), getRole(), getOffset(), getFormat(),
				getTcFormat());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AssetClip o) {
		if (o == null) {
			return 1;
		}

		return Integer.compare(getId(), o.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof AssetClip)) {
			return false;
		}

		AssetClip assetClip = (AssetClip) o;

		return getId() == assetClip.getId();
	}
}
