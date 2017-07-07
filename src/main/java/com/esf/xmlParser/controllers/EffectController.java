package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.database.DatabaseConnector;
import com.esf.xmlParser.entities.Effect;

public class EffectController {

	private DatabaseConnector db;

	public EffectController(String name) {
		db = new DatabaseConnector(name);
	}

	public void addEffects(List<Effect> effects) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO EFFECTS VALUES (?, ?, ?, ?);");
		for (Effect effect: effects) {
			ps.setString(1, effect.getId());
			ps.setString(2, effect.getName());
			ps.setString(3, effect.getUid());
			ps.setString(4, effect.getSrc());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		conn.close();
	}

	public List<Effect> getEffects() throws SQLException, ClassNotFoundException {
		List<Effect> effects = new ArrayList<Effect>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM EFFECTS;");
		while (rs.next()) {
			effects.add(createEffect(rs));
		}

		rs.close();
		conn.close();

		return effects;
	}

	public Effect getEffect(String id) throws SQLException, ClassNotFoundException {
		Effect effect = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM EFFECTS WHERE EFFECTS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			effect = createEffect(rs);
		}

		rs.close();
		conn.close();

		return effect;
	}
	
	private Effect createEffect(ResultSet rs) throws SQLException {
		Effect effect = new Effect();

		effect.setId(rs.getString("id"));
		effect.setName(rs.getString("name"));
		effect.setUid(rs.getString("uid"));
		effect.setSrc(rs.getString("src"));

		return effect;
	}
}
