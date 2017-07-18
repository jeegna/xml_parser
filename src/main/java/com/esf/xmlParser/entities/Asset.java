package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Asset tag in FCPXML.
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
	private StringProperty uid;
	private StringProperty src;
	private StringProperty start;
	private Format format;
	private IntegerProperty audioSources;
	private IntegerProperty audioChannels;
	private IntegerProperty audioRate;

	/**
	 * Instantiates a new asset clip entity.
	 */
	public Asset() {
		id = new SimpleStringProperty();
		duration = new SimpleStringProperty();
		hasVideo = new SimpleBooleanProperty();
		hasAudio = new SimpleBooleanProperty();
		uid = new SimpleStringProperty();
		src = new SimpleStringProperty();
		start = new SimpleStringProperty();
		format = new Format();
		audioSources = new SimpleIntegerProperty();
		audioChannels = new SimpleIntegerProperty();
		audioRate = new SimpleIntegerProperty();
	}

	/**
	 * Gets the id property.
	 *
	 * @return The id property.
	 */
	public StringProperty idProperty() {
		return id;
	}

	/**
	 * Gets the id.
	 *
	 * @return The id.
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            The new id to set.
	 */
	public void setId(String id) {
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
	 * Gets the has video property.
	 *
	 * @return The has video property.
	 */
	public BooleanProperty hasVideoProperty() {
		return hasVideo;
	}

	/**
	 * Gets the has video.
	 *
	 * @return The has video.
	 */
	public boolean hasVideo() {
		return hasVideo.get();
	}

	/**
	 * Sets the has video.
	 *
	 * @param hasVideo
	 *            The new has video to set.
	 */
	public void setHasVideo(boolean hasVideo) {
		this.hasVideo.set(hasVideo);
	}

	/**
	 * Gets the has audio property.
	 *
	 * @return The has audio property.
	 */
	public BooleanProperty hasAudioProperty() {
		return hasAudio;
	}

	/**
	 * Gets the has audio.
	 *
	 * @return The has audio.
	 */
	public boolean hasAudio() {
		return hasAudio.get();
	}

	/**
	 * Sets the has audio.
	 *
	 * @param hasAudio
	 *            The new hasAaudio to set.
	 */
	public void setHasAudio(boolean hasAudio) {
		this.hasAudio.set(hasAudio);
	}

	/**
	 * Gets the UID property.
	 *
	 * @return The UID property.
	 */
	public StringProperty uidProperty() {
		return uid;
	}

	/**
	 * Gets the UID.
	 *
	 * @return The UID.
	 */
	public String getUid() {
		return uid.get();
	}

	/**
	 * Sets the UID.
	 *
	 * @param uid
	 *            The new UID to set.
	 */
	public void setUid(String uid) {
		if (uid == null) {
			uid = "";
		}
		this.uid.set(uid);
	}

	/**
	 * Gets the source property.
	 *
	 * @return The source property.
	 */
	public StringProperty srcProperty() {
		return src;
	}

	/**
	 * Gets the source.
	 *
	 * @return The source.
	 */
	public String getSrc() {
		return src.get();
	}

	/**
	 * Sets the source.
	 *
	 * @param src
	 *            The new source to set.
	 */
	public void setSrc(String src) {
		if (src == null) {
			src = "";
		}
		this.src.set(src);
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
	 * Sets the start time.
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
	 * Gets the audio sources property.
	 *
	 * @return The audio sources property.
	 */
	public IntegerProperty audioSourcesProperty() {
		return audioSources;
	}

	/**
	 * Gets the audio sources.
	 *
	 * @return The audio sources.
	 */
	public int getAudioSources() {
		return audioSources.get();
	}

	/**
	 * Sets the audio sources.
	 *
	 * @param audioSources
	 *            The new audio sources to set.
	 */
	public void setAudioSources(int audioSources) {
		this.audioSources.set(audioSources);
	}

	/**
	 * Gets the audio channels property.
	 *
	 * @return The audio channels property.
	 */
	public IntegerProperty audioChannelsProperty() {
		return audioChannels;
	}

	/**
	 * Gets the audio channels.
	 *
	 * @return The audio channels.
	 */
	public int getAudioChannels() {
		return audioChannels.get();
	}

	/**
	 * Sets the audio channels.
	 *
	 * @param audioChannels
	 *            The new audio channels to set.
	 */
	public void setAudioChannels(int audioChannels) {
		this.audioChannels.set(audioChannels);
	}

	/**
	 * Gets the audio rate property.
	 *
	 * @return The audio rate property.
	 */
	public IntegerProperty audioRateProperty() {
		return audioRate;
	}

	/**
	 * Gets the audio rate.
	 *
	 * @return The audio rate.
	 */
	public int getAudioRate() {
		return audioRate.get();
	}

	/**
	 * Sets the audio rate.
	 *
	 * @param audioRate
	 *            The new audio rate to set.
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
				getClass().getSimpleName()
						+ " [id=%s, duration=%s, hasVideo=%s, hasAudio=%s, name=%s, uid=%s, src=%s, start=%s, format=%s, audioSources=%s, audioChannels=%s, audioRate=%s]",
				getId(), getDuration(), hasVideo(), hasAudio(), getName(), getUid(), getSrc(), getStart(), getFormat(),
				getAudioSources(), getAudioChannels(), getAudioRate());
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

		return Integer.compare(Integer.parseInt(getId().substring(1)), (Integer.parseInt(o.getId().substring(1))));
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

		return getId().equals(asset.getId());
	}
}
