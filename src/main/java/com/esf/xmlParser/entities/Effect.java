package com.esf.xmlParser.entities;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Effect implements Serializable {

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
	 * @return the id
	 */
	public String getId() {
		return id.get();
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
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
	 * @return the uid
	 */
	public String getUid() {
		return uid.get();
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
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
	 * @return the src
	 */
	public String getSrc() {
		return src.get();
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src.set(src);
	}
}
