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
import com.esf.xmlParser.entities.Effect;

// TODO: Auto-generated Javadoc
/**
 * The Class EffectController.
 */
public class EffectController {

	/** The logger. */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/** The db. */
	private DatabaseController db;

	/**
	 * Instantiates a new effect controller.
	 *
	 * @param db
	 *            the db
	 */
	public EffectController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the effects.
	 *
	 * @param effects
	 *            the effects
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addEffects(List<Effect> effects) throws SQLException, ClassNotFoundException {
		logger.info("Adding Effects...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO EFFECTS VALUES (?, ?, ?, ?);");
		for (Effect effect : effects) {
			logger.info("Adding " + effect);
			ps.setString(1, effect.getId());
			ps.setString(2, effect.getName());
			ps.setString(3, effect.getUid());
			ps.setString(4, effect.getSrc());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	/**
	 * Gets the effects.
	 *
	 * @return The effects.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffects() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Effects from database...");
		return getAll();
	}

	/**
	 * Gets the effects by id.
	 *
	 * @param key
	 *            the key
	 * @return The effects by id.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with id: " + key);
		return get(DatabaseController.ID, key);
	}

	/**
	 * Gets the effects by name.
	 *
	 * @param key
	 *            the key
	 * @return The effects by name.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * Gets the effects by uid.
	 *
	 * @param key
	 *            the key
	 * @return The effects by uid.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsByUid(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with UID like " + key);
		return get(DatabaseController.UID, key);
	}

	/**
	 * Gets the effects by src.
	 *
	 * @param key
	 *            the key
	 * @return The effects by src.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsBySrc(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with source like " + key);
		return get(DatabaseController.SOURCE, key);
	}

	/**
	 * Gets the.
	 *
	 * @param col
	 *            the col
	 * @param key
	 *            the key
	 * @return the list
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Effect> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Effect> effects = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.EFFECTS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Effect effect = createEffect(rs);
			effects.add(effect);

			logger.info("Found " + effect);
		}

		ps.close();
		rs.close();
		conn.close();

		return effects;
	}

	/**
	 * Gets the all.
	 *
	 * @return The all.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Effect> getAll() throws SQLException, ClassNotFoundException {
		List<Effect> effects = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.EFFECTS + ";");
		while (rs.next()) {
			Effect effect = createEffect(rs);
			effects.add(effect);

			logger.info("Found " + effect);
		}

		stmt.close();
		rs.close();
		conn.close();

		return effects;
	}

	/**
	 * Creates the effect.
	 *
	 * @param rs
	 *            the rs
	 * @return the effect
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	private Effect createEffect(ResultSet rs) throws SQLException {
		logger.info("Creating Effect...");

		Effect effect = new Effect();

		effect.setId(rs.getString(DatabaseController.ID));
		effect.setName(rs.getString(DatabaseController.NAME));
		effect.setUid(rs.getString(DatabaseController.UID));
		effect.setSrc(rs.getString(DatabaseController.SOURCE));

		logger.info("Created " + effect);
		return effect;
	}
}
