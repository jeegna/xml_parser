package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Format;

public class FormatController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public FormatController(DatabaseController db) {
		this.db = db;
	}

	public void addFormats(List<Format> formats) throws SQLException, ClassNotFoundException {
		logger.info("Adding Formats...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO FORMATS VALUES (?, ?, ?, ?, ?);");
		for (Format format : formats) {
			logger.info("Adding " + format);
			ps.setString(1, format.getId());
			ps.setString(2, format.getName());
			ps.setInt(3, format.getWidth());
			ps.setInt(4, format.getHeight());
			ps.setString(5, format.getFrameDuration());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	public List<Format> getFormats() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Formats from database...");

		List<Format> formats = new ArrayList<Format>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM FORMATS;");
		while (rs.next()) {
			formats.add(createFormat(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return formats;
	}

	public List<Format> getFormats(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with name like " + name);
		name = "%" + name + "%";

		List<Format> formats = new ArrayList<Format>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM FORMATS WHERE FORMATS.name LIKE ?;");
		ps.setString(1, name);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Format format = createFormat(rs);
			formats.add(format);

			logger.info("Found " + format);
		}

		ps.close();
		rs.close();
		conn.close();

		return formats;
	}

	public Format getFormat(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Format with id " + id);

		Format format = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM FORMATS WHERE FORMATS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			format = createFormat(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + format);
		return format;
	}

	private Format createFormat(ResultSet rs) throws SQLException {
		logger.info("Creating Format...");

		Format format = new Format();

		format.setId(rs.getString("id"));
		format.setName(rs.getString("name"));
		format.setWidth(rs.getInt("width"));
		format.setHeight(rs.getInt("height"));
		format.setFrameDuration(rs.getString("frameDuration"));

		logger.info("Created " + format);
		return format;
	}
}
