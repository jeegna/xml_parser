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

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO " + DatabaseController.ASSETS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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
		return getAll();
	}

	public List<Asset> getAssetsByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with name like " + name);
		name = "%" + name + "%";

		return get(DatabaseController.NAME, name);
	}

	public Asset getAssetById(String id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset with id " + id);
		List<Asset> assets = get(DatabaseController.ID, id);

		if (assets != null && !assets.isEmpty()) {
			return assets.get(0);
		} else {
			return null;
		}
	}

	private List<Asset> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Asset> assets = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.ASSETS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Asset asset = createAsset(rs);
			assets.add(asset);

			logger.info("Found " + asset);
		}

		ps.close();
		rs.close();
		conn.close();

		return assets;
	}

	private List<Asset> getAll() throws SQLException, ClassNotFoundException {
		List<Asset> assets = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.ASSETS + ";");
		while (rs.next()) {
			Asset asset = createAsset(rs);
			assets.add(asset);

			logger.info("Found " + asset);
		}

		stmt.close();
		rs.close();
		conn.close();

		return assets;
	}

	private Asset createAsset(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset...");

		Asset asset = new Asset();
		asset.setId(rs.getString(DatabaseController.ID));
		asset.setDuration(rs.getString(DatabaseController.DURATION));
		asset.setHasVideo(rs.getBoolean(DatabaseController.HAS_VIDEO));
		asset.setHasAudio(rs.getBoolean(DatabaseController.HAS_AUDIO));
		asset.setName(rs.getString(DatabaseController.NAME));
		asset.setUid(rs.getString(DatabaseController.UID));
		asset.setSrc(rs.getString(DatabaseController.SOURCE));
		asset.setStart(rs.getString(DatabaseController.START));
		asset.setAudioSources(rs.getInt(DatabaseController.AUDIO_SOURCES));
		asset.setAudioChannels(rs.getInt(DatabaseController.AUDIO_CHANNELS));
		asset.setAudioRate(rs.getInt(DatabaseController.AUDIO_RATE));

		Format format = db.getFormatById(rs.getString(DatabaseController.FORMAT_ID));
		asset.setFormat(format);

		logger.info("Created " + asset);
		return asset;
	}
}
