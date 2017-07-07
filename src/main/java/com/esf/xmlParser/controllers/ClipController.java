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

	public ClipController(DatabaseController db) {
		this.db = db;
	}

	public void addClips(List<Clip> clips) throws SQLException, ClassNotFoundException {
		logger.info("Adding Clips...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO CLIPS VALUES (?, ?, ?, ?, ?, ?);");
		for (Clip clip : clips) {
			logger.info("Adding " + clip);
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

		ps.close();
		conn.close();
	}

	public List<Clip> getClips() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Clips from database...");

		List<Clip> clips = new ArrayList<Clip>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM CLIPS;");
		while (rs.next()) {
			clips.add(createClip(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return clips;
	}

	public Clip getClip(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clip with id " + id);

		Clip clip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIPS WHERE CLIPS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			clip = createClip(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + clip);
		return clip;
	}

	private Clip createClip(ResultSet rs) throws SQLException {
		logger.info("Creating Clip...");

		Clip clip = new Clip();

		clip.setId(rs.getInt("id"));
		clip.setName(rs.getString("name"));
		clip.setOffset(rs.getString("offset"));
		clip.setDuration(rs.getString("duration"));
		clip.setStart(rs.getString("start"));
		clip.setTcFormat(rs.getString("tcFormat"));

		logger.info("Created " + clip);
		return clip;
	}
}
