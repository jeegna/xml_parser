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

		List<Effect> effects = new ArrayList<Effect>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM EFFECTS;");
		while (rs.next()) {
			effects.add(createEffect(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return effects;
	}

	public List<Effect> getEffects(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effects with name like " + name);
		name = "%" + name + "%";

		List<Effect> effects = new ArrayList<Effect>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM EFFECTS WHERE EFFECTS.name LIKE ?;");
		ps.setString(1, name);

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

	public Effect getEffect(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Effect with id: " + id);

		Effect effect = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM EFFECTS WHERE EFFECTS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			effect = createEffect(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + effect);
		return effect;
	}

	private Effect createEffect(ResultSet rs) throws SQLException {
		logger.info("Creating Effect...");

		Effect effect = new Effect();

		effect.setId(rs.getString("id"));
		effect.setName(rs.getString("name"));
		effect.setUid(rs.getString("uid"));
		effect.setSrc(rs.getString("src"));

		logger.info("Created " + effect);
		return effect;
	}
}
