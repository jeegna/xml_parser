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
		return getAll();
	}

	public List<Audio> getAudiosById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with id " + key);
		return get(DatabaseController.ID, String.valueOf(key));
	}

	public List<Audio> getAudiosByLane(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with lane like " + key);
		return get(DatabaseController.LANE, key);
	}

	public List<Audio> getAudiosByRole(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with role like " + key);
		return get(DatabaseController.ROLE, key);
	}

	public List<Audio> getAudiosByOffset(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with offset like " + key);
		return get(DatabaseController.OFFSET, key);
	}

	public List<Audio> getAudiosByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	public List<Audio> getAudiosByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with start like " + key);
		return get(DatabaseController.START, key);
	}

	public List<Audio> getAudiosBySrcCh(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with source channel like " + key);
		return get(DatabaseController.SOURCE_CHANNEL, key);
	}

	public List<Audio> getAudiosBySrcId(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with source ID like " + key);
		return get(DatabaseController.SOURCE_ID, key);
	}

	private List<Audio> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Audio> audios = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.AUDIOS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Audio audio = createAudio(rs);
			audios.add(audio);

			logger.info("Found " + audio);
		}

		ps.close();
		rs.close();
		conn.close();

		return audios;
	}

	private List<Audio> getAll() throws SQLException, ClassNotFoundException {
		List<Audio> audios = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.AUDIOS + ";");
		while (rs.next()) {
			Audio audio = createAudio(rs);
			audios.add(audio);

			logger.info("Found " + audio);
		}

		stmt.close();
		rs.close();
		conn.close();

		return audios;
	}

	private Audio createAudio(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Audio...");

		Audio audio = new Audio();
		audio.setId(rs.getInt(DatabaseController.ID));
		audio.setLane(rs.getInt(DatabaseController.LANE));
		audio.setRole(rs.getString(DatabaseController.ROLE));
		audio.setOffset(rs.getString(DatabaseController.OFFSET));
		audio.setDuration(rs.getString(DatabaseController.DURATION));
		audio.setStart(rs.getString(DatabaseController.START));
		audio.setSrcCh(rs.getString(DatabaseController.SOURCE_CHANNEL));
		audio.setSrcId(rs.getInt(DatabaseController.SOURCE_ID));

		List<Asset> assets = db.getAssetsById(rs.getString(DatabaseController.ASSET_ID));
		Asset asset = null;
		if (assets != null && !assets.isEmpty()) {
			asset = assets.get(0);
		}
		audio.setAsset(asset);

		logger.info("Created " + audio);
		return audio;
	}
}
