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
import com.esf.xmlParser.entities.Clip;

public class ClipController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * @param db
	 */
	public ClipController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * @param clips
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addClips(List<Clip> clips) throws SQLException, ClassNotFoundException {
		logger.info("Adding Clips...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO CLIPS VALUES (null, ?, ?, ?, ?, ?);");
		for (Clip clip : clips) {
			logger.info("Adding " + clip);
			ps.setString(1, clip.getName());
			ps.setString(2, clip.getOffset());
			ps.setString(3, clip.getDuration());
			ps.setString(4, clip.getStart());
			ps.setString(5, clip.getTcFormat());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClips() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Clips from database...");
		return getAll();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsById(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clip with id " + id);
		return get(DatabaseController.ID, id);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByOffset(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with offset like " + key);
		return get(DatabaseController.OFFSET, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with start like " + key);
		return get(DatabaseController.START, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByTcFormat(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with TC formats like " + key);
		return get(DatabaseController.TC_FORMAT, key);
	}

	/**
	 * @param col
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Clip> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Clip> clips = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.CLIPS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Clip clip = createClip(rs);
			clips.add(clip);

			logger.info("Found " + clip);
		}

		ps.close();
		rs.close();
		conn.close();

		return clips;
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Clip> getAll() throws SQLException, ClassNotFoundException {
		List<Clip> clips = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.CLIPS + ";");
		while (rs.next()) {
			Clip clip = createClip(rs);
			clips.add(clip);

			logger.info("Found " + clip);
		}

		stmt.close();
		rs.close();
		conn.close();

		return clips;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	private Clip createClip(ResultSet rs) throws SQLException {
		logger.info("Creating Clip...");

		Clip clip = new Clip();
		clip.setId(rs.getInt(DatabaseController.ID));
		clip.setName(rs.getString(DatabaseController.NAME));
		clip.setOffset(rs.getString(DatabaseController.OFFSET));
		clip.setDuration(rs.getString(DatabaseController.DURATION));
		clip.setStart(rs.getString(DatabaseController.START));
		clip.setTcFormat(rs.getString(DatabaseController.TC_FORMAT));

		logger.info("Created " + clip);
		return clip;
	}
}
