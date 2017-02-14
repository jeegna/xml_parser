package com.esf.xmlParser.entities;

/**
 * @author jeegna
 *
 */
public class Audio implements Comparable<Audio> {

	private String ref;
	private int lane;
	private String role;
	private String offset;
	private String duration;
	private String start;
	private String srcCh;
	private int srcID;

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
	 * Gets the srcCh
	 *
	 * @return The srcCh
	 */
	public String getSrcCh() {
		return srcCh;
	}

	/**
	 * Sets the srcCh
	 *
	 * @param srcCh
	 *            The new srcCh to set
	 */
	public void setSrcCh(String srcCh) {
		this.srcCh = srcCh;
	}

	/**
	 * Gets the srcID
	 *
	 * @return The srcID
	 */
	public int getSrcID() {
		return srcID;
	}

	/**
	 * Sets the srcID
	 *
	 * @param srcID
	 *            The new srcID to set
	 */
	public void setSrcID(int srcID) {
		this.srcID = srcID;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Video[ref=%s, lane=%s, duration=%s, start=%s, role=%s, offset=%s, srcCh=%s, srcId=%s]",
				ref, lane, duration, start, role, offset, srcCh, srcID);
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

		return Integer.compare(Integer.parseInt(ref.substring(1)), (Integer.parseInt(o.ref.substring(1))));
	}
}
