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

	public List<Video> getVideosById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Video with id " + key);
		return get(DatabaseController.ID, String.valueOf(key));
	}

	public List<Video> getVideosByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	public List<Video> getVideosByLane(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with lane like " + key);
		return get(DatabaseController.LANE, key);
	}

	public List<Video> getVideosByOffset(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with offset like " + key);
		return get(DatabaseController.OFFSET, key);
	}

	public List<Video> getVideosByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	public List<Video> getVideosByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with start like " + key);
		return get(DatabaseController.START, key);
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

		List<Asset> assets = db.getAssetsById(rs.getString(DatabaseController.ASSET_ID));
		Asset asset = null;
		if (assets != null && !assets.isEmpty()) {
			asset = assets.get(0);
		}
		video.setAsset(asset);

		logger.info("Created " + video);
		return video;
	}
}
