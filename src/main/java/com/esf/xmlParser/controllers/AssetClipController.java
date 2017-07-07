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

		PreparedStatement ps = conn.prepareStatement("INSERT INTO ASSET_CLIPS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (AssetClip assetClip : assetClips) {
			logger.info("Adding " + assetClip);
			ps.setInt(1, assetClip.getId());
			ps.setString(2, assetClip.getAsset().getId());
			ps.setString(3, assetClip.getName());
			ps.setInt(4, assetClip.getLane());
			ps.setString(5, assetClip.getOffset());
			ps.setString(6, assetClip.getDuration());
			ps.setString(7, assetClip.getStart());
			ps.setString(8, assetClip.getRole());
			ps.setString(9, assetClip.getFormat().getId());
			ps.setString(10, assetClip.getTcFormat());
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

		ResultSet rs = stmt
				.executeQuery("SELECT * FROM ASSET_CLIPS INNER JOIN FORMAT ON ASSET_CLIPS.formatId = FORMATS.id;");
		while (rs.next()) {
			assetClips.add(createAssetClip(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return assetClips;
	}

	public AssetClip getAssetClip(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clip with id " + id);

		AssetClip assetClip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM ASSET_CLIPS INNER JOIN FORMAT ON ASSET_CLIPS.formatId = FORMATS.id INNER JOIN ASSET ON ASSET_CLIPS.ref = ASSETS.id WHERE ASSET_CLIPS.ref=?;");
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

	private AssetClip createAssetClip(ResultSet rs) throws SQLException {
		logger.info("Creating Asset Clip...");

		AssetClip assetClip = new AssetClip();

		assetClip.setId(rs.getInt("id"));
		assetClip.setName(rs.getString("nameVideo"));
		assetClip.setLane(rs.getInt("lane"));
		assetClip.setOffset(rs.getString("Offset"));
		assetClip.setDuration(rs.getString("duration"));
		assetClip.setStart(rs.getString("start"));
		assetClip.setRole(rs.getString("role"));
		assetClip.setTcFormat(rs.getString("tcFormat"));

		// TODO Get format
		Asset asset = new Asset();
		assetClip.setAsset(asset);

		Format format = new Format();
		assetClip.setFormat(format);

		logger.info("Created " + assetClip);
		return assetClip;
	}
}
