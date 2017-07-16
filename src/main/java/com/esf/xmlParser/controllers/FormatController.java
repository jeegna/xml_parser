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
		return getAll();
	}

	public List<Format> getFormatsById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Format with id " + key);
		return get(DatabaseController.ID, key);
	}

	public List<Format> getFormatsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	public List<Format> getFormatsByWidth(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with width like " + key);
		return get(DatabaseController.WIDTH, key);
	}

	public List<Format> getFormatsByHeight(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with height like " + key);
		return get(DatabaseController.HEIGHT, key);
	}

	public List<Format> getFormatsByFrameRate(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with frame rate like " + key);
		return get(DatabaseController.FRAME_RATE, key);
	}

	private List<Format> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Format> formats = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.FORMATS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

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

	private List<Format> getAll() throws SQLException, ClassNotFoundException {
		List<Format> formats = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.FORMATS + ";");
		while (rs.next()) {
			Format format = createFormat(rs);
			formats.add(format);

			logger.info("Found " + format);
		}

		stmt.close();
		rs.close();
		conn.close();

		return formats;
	}

	private Format createFormat(ResultSet rs) throws SQLException {
		logger.info("Creating Format...");

		Format format = new Format();

		format.setId(rs.getString(DatabaseController.ID));
		format.setName(rs.getString(DatabaseController.NAME));
		format.setWidth(rs.getInt(DatabaseController.WIDTH));
		format.setHeight(rs.getInt(DatabaseController.HEIGHT));
		format.setFrameDuration(rs.getString(DatabaseController.FRAME_RATE));

		logger.info("Created " + format);
		return format;
	}
}
