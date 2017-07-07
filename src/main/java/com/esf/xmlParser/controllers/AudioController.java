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
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.Audio;

public class AudioController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public AudioController(DatabaseController db) {
		this.db = db;
	}

	public void addAudios(List<Audio> audios) throws SQLException, ClassNotFoundException {
		logger.info("Adding Audios...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO AUDIOS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Audio audio : audios) {
			logger.info("Adding " + audio);
			ps.setInt(1, audio.getId());
			ps.setString(2, audio.getAsset().getId());
			ps.setInt(3, audio.getLane());
			ps.setString(4, audio.getRole());
			ps.setString(5, audio.getOffset());
			ps.setString(6, audio.getDuration());
			ps.setString(7, audio.getStart());
			ps.setString(8, audio.getSrcCh());
			ps.setInt(9, audio.getSrcId());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	public List<Audio> getAudios() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Audios from database...");

		List<Audio> audios = new ArrayList<Audio>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM AUDIOS INNER JOIN ASSETS ON AUDIOS.assetId=ASSETS.id;");
		while (rs.next()) {
			audios.add(createAudio(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return audios;
	}

	public Audio getAudio(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with id " + id);

		Audio audio = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM AUDIOS INNER JOIN ASSETS ON AUDIOS.assetId = ASSETS.id WHERE AUDIOS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			audio = createAudio(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + audio);
		return audio;
	}

	private Audio createAudio(ResultSet rs) throws SQLException {
		logger.info("Creating Audio...");

		Audio audio = new Audio();

		audio.setId(rs.getInt("id"));
		audio.setLane(rs.getInt("lane"));
		audio.setRole(rs.getString("role"));
		audio.setOffset(rs.getString("offset"));
		audio.setDuration(rs.getString("duration"));
		audio.setStart(rs.getString("start"));
		audio.setSrcCh(rs.getString("srcCh"));
		audio.setSrcId(rs.getInt("srcId"));

		// TODO Get format
		Asset asset = new Asset();
		audio.setAsset(asset);

		logger.info("Created " + audio);
		return audio;
	}
}
