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
import com.esf.xmlParser.entities.Format;

// TODO: Auto-generated Javadoc
/**
 * The Class AssetController.
 */
public class AssetController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * Instantiates a new asset controller.
	 *
	 * @param db
	 *            the db
	 */
	public AssetController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the assets.
	 *
	 * @param assets
	 *            the assets
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addAssets(List<Asset> assets) throws SQLException, ClassNotFoundException {
		logger.info("Adding Assets...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO " + DatabaseController.ASSETS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (Asset asset : assets) {
			logger.info("Adding " + asset);
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

		ps.close();
		conn.close();
	}

	/**
	 * Gets the assets.
	 *
	 * @return The assets.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssets() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Assets from database...");
		return getAll();
	}

	/**
	 * Gets the assets by id.
	 *
	 * @param key
	 *            the key
	 * @return The assets by id.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset with id " + key);
		return get(DatabaseController.ID, key);
	}

	/**
	 * Gets the assets by name.
	 *
	 * @param key
	 *            the key
	 * @return The assets by name.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * Gets the assets by duration.
	 *
	 * @param key
	 *            the key
	 * @return The assets by duration.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	/**
	 * Gets the assets by has audio.
	 *
	 * @param key
	 *            the key
	 * @return The assets by has audio.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByHasAudio(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with hasAudio like " + key);
		return get(DatabaseController.HAS_AUDIO, key);
	}

	/**
	 * Gets the assets by has video.
	 *
	 * @param key
	 *            the key
	 * @return The assets by has video.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByHasVideo(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with hasVideo like " + key);
		return get(DatabaseController.HAS_VIDEO, key);
	}

	/**
	 * Gets the assets by UID.
	 *
	 * @param key
	 *            the key
	 * @return The assets by UID.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByUID(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with UID like " + key);
		return get(DatabaseController.UID, key);
	}

	/**
	 * Gets the assets by src.
	 *
	 * @param key
	 *            the key
	 * @return The assets by src.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsBySrc(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with src like " + key);
		return get(DatabaseController.SOURCE, key);
	}

	/**
	 * Gets the assets by start.
	 *
	 * @param key
	 *            the key
	 * @return The assets by start.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Assets with start like " + key);
		return get(DatabaseController.START, key);
	}

	/**
	 * Gets the.
	 *
	 * @param col
	 *            the col
	 * @param key
	 *            the key
	 * @return the list
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Asset> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<Asset> assets = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.ASSETS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Asset asset = createAsset(rs);
			assets.add(asset);

			logger.info("Found " + asset);
		}

		ps.close();
		rs.close();
		conn.close();

		return assets;
	}

	/**
	 * Gets the all.
	 *
	 * @return The all.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private List<Asset> getAll() throws SQLException, ClassNotFoundException {
		List<Asset> assets = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.ASSETS + ";");
		while (rs.next()) {
			Asset asset = createAsset(rs);
			assets.add(asset);

			logger.info("Found " + asset);
		}

		stmt.close();
		rs.close();
		conn.close();

		return assets;
	}

	/**
	 * Creates the asset.
	 *
	 * @param rs
	 *            the rs
	 * @return the asset
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private Asset createAsset(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset...");

		Asset asset = new Asset();
		asset.setId(rs.getString(DatabaseController.ID));
		asset.setDuration(rs.getString(DatabaseController.DURATION));
		asset.setHasVideo(rs.getBoolean(DatabaseController.HAS_VIDEO));
		asset.setHasAudio(rs.getBoolean(DatabaseController.HAS_AUDIO));
		asset.setName(rs.getString(DatabaseController.NAME));
		asset.setUid(rs.getString(DatabaseController.UID));
		asset.setSrc(rs.getString(DatabaseController.SOURCE));
		asset.setStart(rs.getString(DatabaseController.START));
		asset.setAudioSources(rs.getInt(DatabaseController.AUDIO_SOURCES));
		asset.setAudioChannels(rs.getInt(DatabaseController.AUDIO_CHANNELS));
		asset.setAudioRate(rs.getInt(DatabaseController.AUDIO_RATE));

		List<Format> formats = db.getFormatsById(rs.getString(DatabaseController.FORMAT_ID));
		Format format = null;
		if (formats != null && !formats.isEmpty()) {
			format = formats.get(0);
		}
		asset.setFormat(format);

		logger.info("Created " + asset);
		return asset;
	}
}
