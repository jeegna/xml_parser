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

	public List<Audio> getAudiosByLane(int lane) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with lane like " + lane);
		return get(DatabaseController.LANE, "%" + lane + "%");
	}

	public List<Audio> getAudiosByRole(String role) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with role like " + role);
		return get(DatabaseController.ROLE, "%" + role + "%");
	}

	public List<Audio> getAudiosByOffset(String offset) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with offset like " + offset);
		return get(DatabaseController.OFFSET, "%" + offset + "%");
	}

	public List<Audio> getAudiosByDuration(String duration) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with duration like " + duration);
		return get(DatabaseController.DURATION, "%" + duration + "%");
	}

	public List<Audio> getAudiosByStart(String start) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with start like " + start);
		return get(DatabaseController.START, "%" + start + "%");
	}

	public List<Audio> getAudiosBySrcCh(String srcCh) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with source channel like " + srcCh);
		return get(DatabaseController.SOURCE_CHANNEL, "%" + srcCh + "%");
	}

	public List<Audio> getAudiosBySrcId(int srcId) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with source ID like " + srcId);
		return get(DatabaseController.SOURCE_ID, "%" + srcId + "%");
	}

	public Audio getAudioById(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Audio with id " + id);
		List<Audio> audios = get(DatabaseController.ID, String.valueOf(id));

		if (audios != null && !audios.isEmpty()) {
			return audios.get(0);
		} else {
			return null;
		}
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

		Asset asset = db.getAssetById(rs.getString(DatabaseController.ASSET_ID));
		audio.setAsset(asset);

		logger.info("Created " + audio);
		return audio;
	}
}
