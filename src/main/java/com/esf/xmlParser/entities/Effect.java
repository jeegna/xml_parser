package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Effect tag in FCPXML
 *
 * @author Jeegna Patel
 * @version 2017/07/04
 * @since 1.8
 */
public class Effect extends Element implements Comparable<Effect>, Serializable {

	private static final long serialVersionUID = 1L;

	private StringProperty id;
	private StringProperty name;
	private StringProperty uid;
	private StringProperty src;

	public Effect() {
		id = new SimpleStringProperty();
		name = new SimpleStringProperty();
		uid = new SimpleStringProperty();
		src = new SimpleStringProperty();
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
	 * Gest the UID
	 * 
	 * @return The UID
	 */
	public String getUid() {
		return uid.get();
	}

	/**
	 * Sets the UID
	 * 
	 * @param uid
	 *            The new UID to set
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return String.format("Video[id=%s, name=%s, uid=%s, src=%s]", id.get(), name.get(), uid.get(), src.get());
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

		return Integer.compare(Integer.parseInt(id.get().substring(1)), (Integer.parseInt(o.id.get().substring(1))));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Effect) || id.isEmpty().get()) {
			return false;
		}

		Effect e = (Effect) o;

		return id.equals(e.id);
	}
}
