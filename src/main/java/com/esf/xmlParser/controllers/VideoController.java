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

		List<Video> videos = new ArrayList<Video>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM VIDEOS;");
		while (rs.next()) {
			videos.add(createVideo(rs));
		}

		stmt.close();
		rs.close();
		conn.close();

		return videos;
	}
	
	public List<Video> getVideos(String name) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with name like " + name);
		name = "%" + name + "%";

		List<Video> videos = new ArrayList<Video>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM VIDEOS WHERE VIDEOS.name LIKE ?;");
		ps.setString(1, name);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Video video = createVideo(rs);
			videos.add(video);
			
			logger.info("Found: " + video);
		}

		ps.close();
		rs.close();
		conn.close();

		return videos;
	}
	
	public Video getVideo(int id) throws SQLException, ClassNotFoundException {
		logger.info("Getting Video with id " + id);

		Video video = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM VIDEOS WHERE VIDEOS.id=?;");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			video = createVideo(rs);
		}

		ps.close();
		rs.close();
		conn.close();

		logger.info("Found: " + video);
		return video;
	}

	private Video createVideo(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Video...");

		Video video = new Video();

		video.setId(rs.getInt("id"));
		video.setName(rs.getString("name"));
		video.setLane(rs.getInt("lane"));
		video.setOffset(rs.getString("offset"));
		video.setStart(rs.getString("start"));

		AssetController assetController = new AssetController(db);
		Asset asset = assetController.getAsset(rs.getString("assetId"));
		video.setAsset(asset);

		logger.info("Created " + video);
		return video;
	}
}
