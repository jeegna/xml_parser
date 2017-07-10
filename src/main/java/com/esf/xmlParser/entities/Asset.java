package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Asset tag in FCPXML
 *
 * @author Jeegna Patel
 * @version 2017/04/15
 * @since 1.8
 */
public class Asset extends Element implements Comparable<Asset>, Serializable {

	private static final long serialVersionUID = 1L;

	private StringProperty id;
	private StringProperty duration;
	private BooleanProperty hasVideo;
	private BooleanProperty hasAudio;
	private StringProperty name;
	private StringProperty uid;
	private StringProperty src;
	private StringProperty start;
	private Format format;
	private IntegerProperty audioSources;
	private IntegerProperty audioChannels;
	private IntegerProperty audioRate;

	public Asset() {
		id = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		hasVideo = new SimpleBooleanProperty();
		hasAudio = new SimpleBooleanProperty();
		name = new SimpleStringProperty();
		uid = new SimpleStringProperty();
		src = new SimpleStringProperty();
		start = new SimpleStringProperty();
		format = new Format();
		audioSources = new SimpleIntegerProperty();
		audioChannels = new SimpleIntegerProperty();
		audioRate = new SimpleIntegerProperty();
	}

	/**
	 * Gets the id property
	 *
	 * @return The id property
	 */
	public StringProperty idProperty() {
		return id;
	}

	/**
	 * Gets the id
	 *
	 * @return The id
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * Sets the id
	 *
	 * @param id
	 *            The new id to set
	 */
	public void setId(String id) {
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
	 * Gets the hasVideo property
	 *
	 * @return The hasVideo property
	 */
	public BooleanProperty hasVideoProperty() {
		return hasVideo;
	}

	/**
	 * Gets the hasVideo
	 *
	 * @return The hasVideo
	 */
	public boolean hasVideo() {
		return hasVideo.get();
	}

	/**
	 * Sets the hasVideo
	 *
	 * @param hasVideo
	 *            The new hasVideo to set
	 */
	public void setHasVideo(boolean hasVideo) {
		this.hasVideo.set(hasVideo);
	}

	/**
	 * Gets the hasAudio property
	 *
	 * @return The hasAudio property
	 */
	public BooleanProperty hasAudioProperty() {
		return hasAudio;
	}

	/**
	 * Gets the hasAudio
	 *
	 * @return The hasAudio
	 */
	public boolean hasAudio() {
		return hasAudio.get();
	}

	/**
	 * Sets the hasAudio
	 *
	 * @param hasAudio
	 *            The new hasAudio to set
	 */
	public void setHasAudio(boolean hasAudio) {
		this.hasAudio.set(hasAudio);
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
		if (name == null) {
			name = "";
		}
		this.name.set(name);
	}

	/**
	 * Gets the uid property
	 *
	 * @return The uid property
	 */
	public StringProperty uidProperty() {
		return uid;
	}

	/**
	 * Gets the uid
	 *
	 * @return The uid
	 */
	public String getUid() {
		return uid.get();
	}

	/**
	 * Sets the uid
	 *
	 * @param uid
	 *            The new uid to set
	 */
	public void setUid(String uid) {
		if (uid == null) {
			uid = "";
		}
		this.uid.set(uid);
	}

	/**
	 * Gets the src property
	 *
	 * @return The src property
	 */
	public StringProperty srcProperty() {
		return src;
	}

	/**
	 * Gets the src
	 *
	 * @return The src
	 */
	public String getSrc() {
		return src.get();
	}

	/**
	 * Sets the src
	 *
	 * @param src
	 *            The new src to set
	 */
	public void setSrc(String src) {
		if (src == null) {
			src = "";
		}
		this.src.set(src);
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
	 * Gets the Format
	 *
	 * @return The Format
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * Sets the Format
	 *
	 * @param format
	 *            The new Format to set
	 */
	public void setFormat(Format format) {
		if (format == null) {
			format = new Format();
		}
		this.format = format;
	}

	/**
	 * Gets the audioSources property
	 *
	 * @return The audioSources property
	 */
	public IntegerProperty audioSourcesProperty() {
		return audioSources;
	}

	/**
	 * Gets the audioSources
	 *
	 * @return The audioSources
	 */
	public int getAudioSources() {
		return audioSources.get();
	}

	/**
	 * Sets the audioSources
	 *
	 * @param audioSources
	 *            The new audioSources to set
	 */
	public void setAudioSources(int audioSources) {
		this.audioSources.set(audioSources);
	}

	/**
	 * Gets the audioChannels property
	 *
	 * @return The audioChannels property
	 */
	public IntegerProperty audioChannelsProperty() {
		return audioChannels;
	}

	/**
	 * Gets the audioChannels
	 *
	 * @return The audioChannels
	 */
	public int getAudioChannels() {
		return audioChannels.get();
	}

	/**
	 * Sets the audioChannels
	 *
	 * @param audioChannels
	 *            The new audioChannels to set
	 */
	public void setAudioChannels(int audioChannels) {
		this.audioChannels.set(audioChannels);
	}

	/**
	 * Gets the audioRate property
	 *
	 * @return The audioRate property
	 */
	public IntegerProperty audioRateProperty() {
		return audioRate;
	}

	/**
	 * Gets the audioRate
	 *
	 * @return The audioRate
	 */
	public int getAudioRate() {
		return audioRate.get();
	}

	/**
	 * Sets the audioRate
	 *
	 * @param audioRate
	 *            The new audioRate to set
	 */
	public void setAudioRate(int audioRate) {
		this.audioRate.set(audioRate);
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
				id.get(), duration.get(), hasVideo.get(), hasAudio.get(), name.get(), uid.get(), src.get(), start.get(),
				format, audioSources.get(), audioChannels.get(), audioRate.get());
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

		return Integer.compare(Integer.parseInt(id.get().substring(1)), (Integer.parseInt(o.id.get().substring(1))));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Asset)) {
			return false;
		}

		Asset asset = (Asset) o;

		return id.get().equals(asset.id.get());
	}
}
