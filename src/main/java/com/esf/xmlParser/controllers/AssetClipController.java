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
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Format;

public class AssetClipController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public AssetClipController(DatabaseController db) {
		this.db = db;
	}

	public void addAssetClips(List<AssetClip> assetClips) throws SQLException, ClassNotFoundException {
		logger.info("Adding AssetClips...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO " + DatabaseController.ASSET_CLIPS + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (AssetClip assetClip : assetClips) {
			logger.info("Adding " + assetClip);
			ps.setString(1, assetClip.getAsset().getId());
			ps.setString(2, assetClip.getName());
			ps.setInt(3, assetClip.getLane());
			ps.setString(4, assetClip.getOffset());
			ps.setString(5, assetClip.getDuration());
			ps.setString(6, assetClip.getStart());
			ps.setString(7, assetClip.getRole());
			ps.setString(8, assetClip.getFormat().getId());
			ps.setString(9, assetClip.getTcFormat());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	public List<AssetClip> getAssetClips() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Asset Clips from database...");
		return getAll();
	}

	public List<AssetClip> getAssetClipsByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with name like " + name);
		name = "%" + name + "%";

		return get(DatabaseController.NAME, name);
	}

	public AssetClip getAssetClipById(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clip with id " + id);
		List<AssetClip> assetClips = get(DatabaseController.ID, String.valueOf(id));

		if (assetClips != null && !assetClips.isEmpty()) {
			return assetClips.get(0);
		} else {
			return null;
		}
	}

	private List<AssetClip> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<AssetClip> assetClips = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.ASSET_CLIPS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			AssetClip assetClip = createAssetClip(rs);
			assetClips.add(assetClip);

			logger.info("Found " + assetClip);
		}

		ps.close();
		rs.close();
		conn.close();

		return assetClips;
	}

	private List<AssetClip> getAll() throws SQLException, ClassNotFoundException {
		List<AssetClip> assetClips = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.ASSET_CLIPS + ";");
		while (rs.next()) {
			AssetClip assetClip = createAssetClip(rs);
			assetClips.add(assetClip);

			logger.info("Found " + assetClip);
		}

		stmt.close();
		rs.close();
		conn.close();

		return assetClips;
	}

	private AssetClip createAssetClip(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset Clip...");

		AssetClip assetClip = new AssetClip();
		assetClip.setId(rs.getInt(DatabaseController.ID));
		assetClip.setName(rs.getString(DatabaseController.NAME));
		assetClip.setLane(rs.getInt(DatabaseController.LANE));
		assetClip.setOffset(rs.getString(DatabaseController.OFFSET));
		assetClip.setDuration(rs.getString(DatabaseController.DURATION));
		assetClip.setStart(rs.getString(DatabaseController.START));
		assetClip.setRole(rs.getString(DatabaseController.ROLE));
		assetClip.setTcFormat(rs.getString(DatabaseController.TC_FORMAT));

		Asset asset = db.getAssetById(rs.getString(DatabaseController.ASSET_ID));
		assetClip.setAsset(asset);

		Format format = db.getFormatById(rs.getString(DatabaseController.FORMAT_ID));
		assetClip.setFormat(format);

		logger.info("Created " + assetClip);
		return assetClip;
	}
}
