package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.database.DatabaseConnector;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.Audio;

public class AudioController {

	private DatabaseConnector db;

	public AudioController(String name) {
		db = new DatabaseConnector(name);
	}

	public void addAudios(List<Audio> audios) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO AUDIOS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Audio audio : audios) {
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

		conn.close();
	}

	public List<Audio> getAudios() throws SQLException, ClassNotFoundException {
		List<Audio> audios = new ArrayList<Audio>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM AUDIOS INNER JOIN ASSETS ON AUDIOS.assetId=ASSETS.id;");
		while (rs.next()) {
			audios.add(createAudio(rs));
		}

		rs.close();
		conn.close();

		return audios;
	}

	public Audio getAudio(String id) throws SQLException, ClassNotFoundException {
		Audio audio = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM AUDIOS INNER JOIN ASSETS ON AUDIOS.assetId = ASSETS.id WHERE AUDIOS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			audio = createAudio(rs);
		}

		rs.close();
		conn.close();

		return audio;
	}

	private Audio createAudio(ResultSet rs) throws SQLException {
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

		return audio;
	}
}
