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
import com.esf.xmlParser.entities.Format;

public class AssetController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public AssetController(DatabaseController db) {
		this.db = db;
	}

	public void addAssets(List<Asset> assets) throws SQLException, ClassNotFoundException {
		logger.info("Adding Assets...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO ASSETS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Asset asset : assets) {
			logger.info("Adding " + asset);
			ps.setString(1, asset.getId());
			ps.setString(2, asset.getDuration());
			ps.setBoolean(3, asset.hasVideo());
			ps.setBoolean(4, asset.hasAudio());
			ps.setString(5, asset.getName());
			ps.setString(6, asset.getUid());
			ps.setString(7, asset.getSrc());
			ps.setString(8, asset.getStart());
			ps.setString(9, asset.getFormat().getId());
			ps.setInt(10, asset.getAudioSources());
			ps.setInt(11, asset.getAudioChannels());
			ps.setInt(12, asset.getAudioRate());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	public List<Asset> getAssets() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Assets from database...");

		List<Asset> assets = new ArrayList<Asset>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM ASSETS;");
		while (rs.next()) {
			assets.add(createAsset(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return assets;
	}

	public Asset getAsset(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset with id " + id);

		Asset asset = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ASSETS WHERE ASSETS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			asset = createAsset(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + asset);
		return asset;
	}

	private Asset createAsset(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset...");

		Asset asset = new Asset();
		asset.setId(rs.getString("id"));
		asset.setDuration(rs.getString("duration"));
		asset.setHasVideo(rs.getBoolean("hasVideo"));
		asset.setHasAudio(rs.getBoolean("hasAudio"));
		asset.setName(rs.getString("name"));
		asset.setUid(rs.getString("uid"));
		asset.setSrc(rs.getString("src"));
		asset.setStart(rs.getString("start"));
		asset.setAudioSources(rs.getInt("audioSources"));
		asset.setAudioChannels(rs.getInt("audioChannels"));
		asset.setAudioRate(rs.getInt("audioRate"));

		FormatController formatController = new FormatController(db);
		Format format = formatController.getFormat(rs.getString("formatId"));
		asset.setFormat(format);

		logger.info("Created " + asset);
		return asset;
	}
}
