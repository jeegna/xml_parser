package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Effect tag in FCPXML.
 *
 * @author Jeegna Patel
 * @version 2017/07/04
 * @since 1.8
 */
public class Effect extends Element implements Comparable<Effect>, Serializable {

	private static final long serialVersionUID = 1L;

	private StringProperty id;
	private StringProperty uid;
	private StringProperty src;

	/**
	 * Instantiates a new audio entity.
	 */
	public Effect() {
		id = new SimpleStringProperty();
		uid = new SimpleStringProperty();
		src = new SimpleStringProperty();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format(getClass().getSimpleName() + " [id=%s, name=%s, uid=%s, src=%s]", getId(), getName(),
				getUid(), getSrc());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Effect o) {
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
		if (o == null || !(o instanceof Effect)) {
			return false;
		}

		Effect effect = (Effect) o;

		return getId().equals(effect.getId());
	}
}
