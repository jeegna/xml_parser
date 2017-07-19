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

/**
 * The Video controller class. This class offers database access methods for
 * Video entities.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class VideoController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * Instantiates a new video controller.
	 *
	 * @param db
	 *            The database controller to use.
	 */
	public VideoController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the videos.
	 *
	 * @param videos
	 *            A list of videos to add.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addVideos(List<Video> videos) throws SQLException, ClassNotFoundException {
		logger.info("Adding Videos...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO VIDEOS VALUES (null, ?, ?, ?, ?, ?, ?, ?);");
		for (Video video : videos) {
			logger.info("Adding " + video);
			ps.setString(1, video.getName());
			ps.setInt(2, video.getLane());
			ps.setString(3, video.getOffset());
			ps.setString(4, video.getAsset().getId());
			ps.setString(5, video.getDuration());
			ps.setString(6, video.getStart());
			ps.setString(7, video.getTcFormat());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	/**
	 * Gets all the videos in the database.
	 * 
	 * @return A list of all videos in the database.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideos() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Videos from database...");
		return getAll();
	}

	/**
	 * Gets the videos by id.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose id matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Video with id " + key);
		return get(DatabaseController.ID, String.valueOf(key));
	}

	/**
	 * Gets the videos by name.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose name matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * Gets the videos by lane.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose lane matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByLane(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with lane like " + key);
		return get(DatabaseController.LANE, key);
	}

	/**
	 * Gets the videos by offset.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose offset matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByOffset(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with offset like " + key);
		return get(DatabaseController.OFFSET, key);
	}

	/**
	 * Gets the videos by duration.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose duration matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	/**
	 * Gets the videos by start.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose start matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with start like " + key);
		return get(DatabaseController.START, key);
	}

	/**
	 * Gets the videos by TC format.
	 *
	 * @param key
	 *            The key.
	 * @return A list of videos whose TC format matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByTcFormat(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Videos with TC format like " + key);
		return get(DatabaseController.TC_FORMAT, key);
	}

	/**
	 * Gets the video whose given column matches the given key.
	 *
	 * @param col
	 *            The column name to check.
	 * @param key
	 *            The key.
	 * @return A list of videos.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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

	/**
	 * Gets all the videos in the database.
	 *
	 * @return A list of all videos.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
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

	/**
	 * Creates a Video object. This method parses the data in the ResultSet to
	 * create a Video. It will also call upon Asset's findById method to add the
	 * Asset.
	 *
	 * @param rs
	 *            The ResultSet containing the information of the Video.
	 * @return The created video.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private Video createVideo(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Video...");

		Video video = new Video();

		video.setId(rs.getInt(DatabaseController.ID));
		video.setName(rs.getString(DatabaseController.NAME));
		video.setLane(rs.getInt(DatabaseController.LANE));
		video.setOffset(rs.getString(DatabaseController.OFFSET));
		video.setStart(rs.getString(DatabaseController.START));
		video.setDuration(rs.getString(DatabaseController.DURATION));
		video.setTcFormat(rs.getString(DatabaseController.TC_FORMAT));

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
