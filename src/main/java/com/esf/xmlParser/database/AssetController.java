package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.Format;

public class AssetController {

	private DatabaseConnector db;

	public AssetController(String name) {
		db = new DatabaseConnector(name);
	}

	public void addAssets(List<Asset> assets) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO ASSETS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Asset asset : assets) {
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

		conn.close();
	}

	public List<Asset> getAssets() throws SQLException, ClassNotFoundException {
		List<Asset> assets = new ArrayList<Asset>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM ASSETS INNER JOIN FORMAT ON ASSETS.formatId = FORMATS.id;");
		while (rs.next()) {
			assets.add(createAsset(rs));
		}

		rs.close();
		conn.close();

		return assets;
	}

	public Asset getAsset(String id) throws SQLException, ClassNotFoundException {
		Asset asset = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ASSETS INNER JOIN FORMAT ON ASSETS.formatId = FORMATS.id WHERE ASSETS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			asset = createAsset(rs);
		}

		rs.close();
		conn.close();

		return asset;
	}
	
	private Asset createAsset(ResultSet rs) throws SQLException {
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

		// TODO Get format
		Format format = new Format();
		asset.setFormat(format);
		
		return asset;
	}
}
