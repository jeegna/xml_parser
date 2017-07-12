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

		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO ASSET_CLIPS VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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

		List<AssetClip> assetClips = new ArrayList<AssetClip>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM ASSET_CLIPS;");
		while (rs.next()) {
			assetClips.add(createAssetClip(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return assetClips;
	}

	public List<AssetClip> getAssetClipsByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with name like " + name);
		name = "%" + name + "%";

		List<AssetClip> assetClips = new ArrayList<AssetClip>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ASSET_CLIPS WHERE ASSET_CLIPS.name LIKE ?;");
		ps.setString(1, name);

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

	public AssetClip getAssetClip(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clip with id " + id);

		AssetClip assetClip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ASSET_CLIPS WHERE ASSET_CLIPS.assetId LIKE %?%;");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			assetClip = createAssetClip(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found " + assetClip);
		return assetClip;
	}

	private AssetClip createAssetClip(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset Clip...");

		AssetClip assetClip = new AssetClip();
		assetClip.setId(rs.getInt("id"));
		assetClip.setName(rs.getString("name"));
		assetClip.setLane(rs.getInt("lane"));
		assetClip.setOffset(rs.getString("offset"));
		assetClip.setDuration(rs.getString("duration"));
		assetClip.setStart(rs.getString("start"));
		assetClip.setRole(rs.getString("role"));
		assetClip.setTcFormat(rs.getString("tcFormat"));

		AssetController assetController = new AssetController(db);
		Asset asset = assetController.getAsset(rs.getString("assetId"));
		assetClip.setAsset(asset);

		FormatController formatController = new FormatController(db);
		Format format = formatController.getFormat(rs.getString("formatId"));
		assetClip.setFormat(format);

		logger.info("Created " + assetClip);
		return assetClip;
	}
}
