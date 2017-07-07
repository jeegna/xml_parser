package com.esf.xmlParser.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Asset;

public class VideoController {

	private DatabaseController db;

	public VideoController(DatabaseController db) {
		this.db = db;
	}

	public void addVideos(List<Video> videos) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO VIDEOS VALUES (?, ?, ?, ?, ?, ?, ?);");
		for (Video video : videos) {
			ps.setInt(1, video.getId());
			ps.setString(2, video.getName());
			ps.setInt(3, video.getLane());
			ps.setString(4, video.getOffset());
			ps.setString(5, video.getAsset().getId());
			ps.setString(6, video.getDuration());
			ps.setString(7, video.getStart());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		conn.close();
	}

	public List<Video> getVideos() throws SQLException, ClassNotFoundException {
		List<Video> videos = new ArrayList<Video>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM VIDEOS INNER JOIN ASSETS ON ASSETS.id = VIDEOS.id;");
		while (rs.next()) {
			videos.add(createVideo(rs));
		}

		rs.close();
		conn.close();

		return videos;
	}

	public Video getVideo(String id) throws SQLException, ClassNotFoundException {
		Video video = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM VIDEOS INNER JOIN ASSETS ON ASSETS.id = VIDEOS.id WHERE VIDEOS.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			video = createVideo(rs);
		}

		rs.close();
		conn.close();

		return video;
	}

	private Video createVideo(ResultSet rs) throws SQLException {
		Video video = new Video();

		video.setId(rs.getInt("id"));
		video.setName(rs.getString("name"));
		video.setLane(rs.getInt("lane"));
		video.setOffset(rs.getString("offset"));
		video.setStart(rs.getString("start"));

		// TODO Get Asset
		Asset asset = new Asset();
		video.setAsset(asset);

		return video;
	}
}
