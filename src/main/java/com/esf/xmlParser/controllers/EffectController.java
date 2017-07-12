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

public class EffectController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public EffectController(DatabaseController db) {
		this.db = db;
	}

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

	public List<Effect> getEffects() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Effects from database...");
		return getAll();
	}

	public List<Effect> getEffectsByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with name like " + name);
		name = "%" + name + "%";

		return get(DatabaseController.NAME, name);
	}

	public List<Effect> getEffectsByUid(String uid) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with UID like " + uid);
		uid = "%" + uid + "%";

		return get(DatabaseController.UID, uid);
	}

	public Effect getEffectById(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effect with id: " + id);
		List<Effect> effects = get(DatabaseController.ID, id);

		if (effects != null && !effects.isEmpty()) {
			return effects.get(0);
		} else {
			return null;
		}
	}

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
