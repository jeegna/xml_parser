package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Asset;

public class VideoController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	public VideoController(DatabaseController db) {
		this.db = db;
	}

	public void addVideos(List<Video> videos) throws SQLException, ClassNotFoundException {
		logger.info("Adding Videos...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO VIDEOS VALUES (null, ?, ?, ?, ?, ?, ?);");
		for (Video video : videos) {
			logger.info("Adding " + video);
			ps.setString(1, video.getName());
			ps.setInt(2, video.getLane());
			ps.setString(3, video.getOffset());
			ps.setString(4, video.getAsset().getId());
			ps.setString(5, video.getDuration());
			ps.setString(6, video.getStart());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	public List<Video> getVideos() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Videos from database...");
		return getAll();
	}

	public List<Video> getVideosByName(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with name like " + name);
		name = "%" + name + "%";

		return get(DatabaseController.NAME, name);
	}

	public Video getVideoById(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Video with id " + id);
		List<Video> videos = get(DatabaseController.ID, String.valueOf(id));

		if (videos != null && !videos.isEmpty()) {
			return videos.get(0);
		} else {
			return null;
		}
	}

	private List<Video> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Video> videos = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.VIDEOS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Video video = createVideo(rs);
			videos.add(video);

			logger.info("Found " + video);
		}

		ps.close();
		rs.close();
		conn.close();

		return videos;
	}

	private List<Video> getAll() throws SQLException, ClassNotFoundException {
		List<Video> videos = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.VIDEOS + ";");
		while (rs.next()) {
			Video video = createVideo(rs);
			videos.add(video);

			logger.info("Found " + video);
		}

		stmt.close();
		rs.close();
		conn.close();

		return videos;
	}

	private Video createVideo(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Video...");

		Video video = new Video();

		video.setId(rs.getInt(DatabaseController.ID));
		video.setName(rs.getString(DatabaseController.NAME));
		video.setLane(rs.getInt(DatabaseController.LANE));
		video.setOffset(rs.getString(DatabaseController.OFFSET));
		video.setStart(rs.getString(DatabaseController.START));

		Asset asset = db.getAssetById(rs.getString(DatabaseController.ASSET_ID));
		video.setAsset(asset);

		logger.info("Created " + video);
		return video;
	}
}
