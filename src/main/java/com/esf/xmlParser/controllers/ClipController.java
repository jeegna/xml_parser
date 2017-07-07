package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Clip;

public class ClipController {

	private DatabaseController db;

	public ClipController(DatabaseController db) {
		this.db = db;
	}

	public void addClips(List<Clip> clips) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO CLIPS VALUES (?, ?, ?, ?, ?, ?);");
		for (Clip clip : clips) {
			ps.setInt(1, clip.getId());
			ps.setString(2, clip.getName());
			ps.setString(3, clip.getOffset());
			ps.setString(4, clip.getDuration());
			ps.setString(5, clip.getStart());
			ps.setString(6, clip.getTcFormat());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		conn.close();
	}

	public List<Clip> getClips() throws SQLException, ClassNotFoundException {
		List<Clip> clips = new ArrayList<Clip>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM CLIPS;");
		while (rs.next()) {
			clips.add(createClip(rs));
		}

		rs.close();
		conn.close();

		return clips;
	}

	public Clip getClip(String id) throws SQLException, ClassNotFoundException {
		Clip clip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIPS WHERE CLIPS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			clip = createClip(rs);
		}

		rs.close();
		conn.close();

		return clip;
	}
	
	private Clip createClip(ResultSet rs) throws SQLException {
		Clip clip = new Clip();

		clip.setId(rs.getInt("id"));
		clip.setName(rs.getString("name"));
		clip.setOffset(rs.getString("offset"));
		clip.setDuration(rs.getString("duration"));
		clip.setStart(rs.getString("start"));
		clip.setTcFormat(rs.getString("tcFormat"));

		return clip;
	}
}
