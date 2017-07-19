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

/**
 * The Format controller class. This class offers database access methods for
 * Format entities.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class FormatController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * Instantiates a new format controller.
	 *
	 * @param db
	 *            The database controller to use.
	 */
	public FormatController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the formats.
	 *
	 * @param formats
	 *            A list of formats to add.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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

	/**
	 * Gets all the formats in the database.
	 * 
	 * @return A list of all formats in the database.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormats() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Formats from database...");
		return getAll();
	}

	/**
	 * Gets the formats by id.
	 *
	 * @param key
	 *            The key.
	 * @return A list of formats whose id matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Format with id " + key);
		return get(DatabaseController.ID, key);
	}

	/**
	 * Gets the formats by name.
	 *
	 * @param key
	 *            The key.
	 * @return A list of formats whose name matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * Gets the formats by width.
	 *
	 * @param key
	 *            The key.
	 * @return A list of formats whose width matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByWidth(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with width like " + key);
		return get(DatabaseController.WIDTH, key);
	}

	/**
	 * Gets the formats by height.
	 *
	 * @param key
	 *            The key.
	 * @return A list of formats whose height matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByHeight(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with height like " + key);
		return get(DatabaseController.HEIGHT, key);
	}

	/**
	 * Gets the formats by frame rate.
	 *
	 * @param key
	 *            The key.
	 * @return A list of formats whose frame rate matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByFrameRate(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Formats with frame rate like " + key);
		return get(DatabaseController.FRAME_RATE, key);
	}

	/**
	 * Gets the format whose given column matches the given key.
	 *
	 * @param col
	 *            The column name to check.
	 * @param key
	 *            The key.
	 * @return A list of formats.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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

	/**
	 * Gets all the formats in the database.
	 *
	 * @return A list of all formats.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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

	/**
	 * Creates a Format object. This method parses the data in the ResultSet to
	 * create the entity.
	 *
	 * @param rs
	 *            The ResultSet containing the information of the Format.
	 * @return The created format.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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
