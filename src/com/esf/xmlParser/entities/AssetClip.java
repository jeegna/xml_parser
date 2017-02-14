package com.esf.xmlParser.entities;

/**
 * @author Jeegna Patel
 *
 */
public class AssetClip implements Comparable<AssetClip> {

	private String ref;
	private String name;
	private int lane;
	private String offset;
	private String duration;
	private String start;
	private String role;
	private String format;
	private String tcFormat;

	/**
	 * Gets the ref
	 *
	 * @return The ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * Sets the ref
	 *
	 * @param ref
	 *            The new ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * Gets the name
	 *
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 *
	 * @param name
	 *            The new name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the lane
	 *
	 * @return The lane
	 */
	public int getLane() {
		return lane;
	}

	/**
	 * Sets the lane
	 *
	 * @param lane
	 *            The new lane to set
	 */
	public void setLane(int lane) {
		this.lane = lane;
	}

	/**
	 * Gets the offset
	 *
	 * @return The offset
	 */
	public String getOffset() {
		return offset;
	}

	/**
	 * Sets the offset
	 *
	 * @param offset
	 *            The new offset to set
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * Gets the duration
	 *
	 * @return The duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration
	 *
	 * @param duration
	 *            The new duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Gets the start
	 *
	 * @return The start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * Sets the start
	 *
	 * @param start
	 *            The new start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Gets the role
	 *
	 * @return The role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role
	 *
	 * @param role
	 *            The new role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the format
	 *
	 * @return The format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the format
	 *
	 * @param format
	 *            The new format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Gets the tcFormat
	 *
	 * @return The tcFormat
	 */
	public String getTcFormat() {
		return tcFormat;
	}

	/**
	 * Sets the tcFormat
	 *
	 * @param tcFormat
	 *            The new tcFormat to set
	 */
	public void setTcFormat(String tcFormat) {
		this.tcFormat = tcFormat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format(
				"Video[ref=%s, name=%s, lane=%s, duration=%s, start=%s, role=%s, offset=%s, format=%s, tcFormat=%s]",
				ref, name, lane, duration, start, role, offset, format, tcFormat);
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

		return Integer.compare(Integer.parseInt(ref.substring(1)), (Integer.parseInt(o.ref.substring(1))));
	}
}
