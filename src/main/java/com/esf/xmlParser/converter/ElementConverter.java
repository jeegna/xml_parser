package com.esf.xmlParser.converter;

import java.util.logging.Logger;

import com.esf.xmlParser.entities.Element;

import javafx.util.StringConverter;

/**
 * This class provides converter methods for Elements.
 */
public class ElementConverter extends StringConverter<Element> {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.util.StringConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Element element) {
		if (element != null) {
			logger.info("Converting element: " + element);
			return element.getName();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.util.StringConverter#fromString(java.lang.String)
	 */
	@Override
	public Element fromString(String query) {
		logger.info("Converting string: " + query);

		Element element = new Element();
		element.setName(query);

		return element;
	}
}
