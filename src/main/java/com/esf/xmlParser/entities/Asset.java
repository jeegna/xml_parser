package com.esf.xmlParser.entities;

/**
 * @author jeegna
 *
 */
public class Asset implements Comparable<Asset> {

	private String id;
	private String duration;
	private boolean hasVideo;
	private boolean hasAudio;
	private String name;
	private String uid;
	private String src;
	private String start;
	private String format;
	private int audioSources;
	private int audioChannels;
	private int audioRate;

	/**
	 * Gets the id
	 *
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id
	 *
	 * @param id
	 *            The new id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets the hasVideo
	 *
	 * @return The hasVideo
	 */
	public boolean hasVideo() {
		return hasVideo;
	}

	/**
	 * Sets the hasVideo
	 *
	 * @param hasVideo
	 *            The new hasVideo to set
	 */
	public void setHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	/**
	 * Gets the hasAudio
	 *
	 * @return The hasAudio
	 */
	public boolean hasAudio() {
		return hasAudio;
	}

	/**
	 * Sets the hasAudio
	 *
	 * @param hasAudio
	 *            The new hasAudio to set
	 */
	public void setHasAudio(boolean hasAudio) {
		this.hasAudio = hasAudio;
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
	 * Gets the uid
	 *
	 * @return The uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the uid
	 *
	 * @param uid
	 *            The new uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Gets the src
	 *
	 * @return The src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * Sets the src
	 *
	 * @param src
	 *            The new src to set
	 */
	public void setSrc(String src) {
		this.src = src;
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
	 * Gets the audioSources
	 *
	 * @return The audioSources
	 */
	public int getAudioSources() {
		return audioSources;
	}

	/**
	 * Sets the audioSources
	 *
	 * @param audioSources
	 *            The new audioSources to set
	 */
	public void setAudioSources(int audioSources) {
		this.audioSources = audioSources;
	}

	/**
	 * Gets the audioChannels
	 *
	 * @return The audioChannels
	 */
	public int getAudioChannels() {
		return audioChannels;
	}

	/**
	 * Sets the audioChannels
	 *
	 * @param audioChannels
	 *            The new audioChannels to set
	 */
	public void setAudioChannels(int audioChannels) {
		this.audioChannels = audioChannels;
	}

	/**
	 * Gets the audioRate
	 *
	 * @return The audioRate
	 */
	public int getAudioRate() {
		return audioRate;
	}

	/**
	 * Sets the audioRate
	 *
	 * @param audioRate
	 *            The new audioRate to set
	 */
	public void setAudioRate(int audioRate) {
		this.audioRate = audioRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format(
				"Asset[id=%s, duration=%s, hasVideo=%s, hasAudio=%s, name=%s, uid=%s, src=%s, start=%s, format=%s, audioSources=%s, audioChannels=%s, audioRate=%s]",
				id, duration, hasVideo, hasAudio, name, uid, src, start, format, audioSources, audioChannels,
				audioRate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Asset o) {

		if (o == null) {
			return 1;
		}

		return Integer.compare(Integer.parseInt(id.substring(1)), (Integer.parseInt(o.id.substring(1))));
	}
}
