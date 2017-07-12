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

	public List<Clip> getClipsByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with name like " + name);
		name = "%" + name + "%";

		List<Clip> clips = new ArrayList<Clip>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIPS WHERE CLIPS.name LIKE ?;");
		ps.setString(1, name);

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

	public List<Clip> getClipsByTcFormat(String tcFormat) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clips with TC formats like " + tcFormat);
		tcFormat = "%" + tcFormat + "%";

		List<Clip> clips = new ArrayList<Clip>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIPS WHERE CLIPS.tcFormat LIKE ?;");
		ps.setString(1, tcFormat);

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

	public Clip getClip(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Clip with id " + id);

		Clip clip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIPS WHERE CLIPS.id=?;");
		ps.setInt(1, id);

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
