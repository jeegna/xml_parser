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
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Format;

public class AssetClipController {

	private DatabaseConnector db;

	public AssetClipController(String name) {
		db = new DatabaseConnector(name);
	}

	public void addAssetClips(List<AssetClip> assetClips) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO ASSET_CLIPS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (AssetClip assetClip : assetClips) {
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

		conn.close();
	}

	public List<AssetClip> getAssetClips() throws SQLException, ClassNotFoundException {
		List<AssetClip> assetClips = new ArrayList<AssetClip>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat
				.executeQuery("SELECT * FROM ASSET_CLIPS INNER JOIN FORMAT ON ASSET_CLIPS.formatId = FORMATS.id;");
		while (rs.next()) {
			assetClips.add(createAssetClip(rs));
		}

		rs.close();
		conn.close();

		return assetClips;
	}

	public AssetClip getAssetClip(int id) throws SQLException, ClassNotFoundException {
		AssetClip assetClip = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM ASSET_CLIPS INNER JOIN FORMAT ON ASSET_CLIPS.formatId = FORMATS.id INNER JOIN ASSET ON ASSET_CLIPS.ref = ASSETS.id WHERE ASSET_CLIPS.ref=?;");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			assetClip = createAssetClip(rs);
		}

		rs.close();
		conn.close();

		return assetClip;
	}

	private AssetClip createAssetClip(ResultSet rs) throws SQLException {
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

		return assetClip;
	}
}
