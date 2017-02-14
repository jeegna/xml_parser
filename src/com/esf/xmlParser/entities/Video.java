package com.esf.xmlParser.entities;

/**
 * @author jeegna
 *
 */
public class Video implements Comparable<Video> {

	private String name;
	private int lane;
	private String offset;
	private String ref;
	private String duration;
	private String start;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Video[ref=%s, duration=%s, name=%s, start=%s, lane=%s, offset=%s]", ref, duration, name,
				start, lane, offset);
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

		return Integer.compare(Integer.parseInt(ref.substring(1)), (Integer.parseInt(o.ref.substring(1))));
	}
}
