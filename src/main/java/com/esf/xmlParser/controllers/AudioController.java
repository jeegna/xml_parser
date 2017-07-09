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

		PreparedStatement ps = conn.prepareStatement("INSERT INTO AUDIOS VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Audio audio : audios) {
			logger.info("Adding " + audio);
			ps.setString(1, audio.getAsset().getId());
			ps.setInt(2, audio.getLane());
			ps.setString(3, audio.getRole());
			ps.setString(4, audio.getOffset());
			ps.setString(5, audio.getDuration());
			ps.setString(6, audio.getStart());
			ps.setString(7, audio.getSrcCh());
			ps.setInt(8, audio.getSrcId());
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

		ResultSet rs = stmt.executeQuery("SELECT * FROM AUDIOS;");
		while (rs.next()) {
			audios.add(createAudio(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return audios;
	}

	public Audio getAudio(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with id " + id);

		Audio audio = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM AUDIOS WHERE AUDIOS.id=?;");
		ps.setInt(1, id);

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

	private Audio createAudio(ResultSet rs) throws SQLException, ClassNotFoundException {
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

		AssetController assetController = new AssetController(db);
		Asset asset = assetController.getAsset(rs.getString("assetId"));
		audio.setAsset(asset);

		logger.info("Created " + audio);
		return audio;
	}
}
