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

/**
 * The Asset controller class. This class offers database access methods for
 * Asset entities.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class AssetController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * Instantiates a new asset controller.
	 *
	 * @param db
	 *            The database controller to use.
	 */
	public AssetController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the assets.
	 *
	 * @param assets
	 *            A list of assets to add.
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
	 * Gets all the assets in the database.
	 * 
	 * @return A list of all assets in the database.
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
	 *            The key.
	 * @return A list of assets whose id matches the key.
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
	 *            The key.
	 * @return A list of assets whose name matches the key.
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
	 *            The key.
	 * @return A list of assets whose duration matches the key.
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
	 *            The key.
	 * @return A list of assets whose has audio matches the key.
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
	 *            The key.
	 * @return A list of assets whose has video matches the key.
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
	 * @return A list of assets whose UID field matches the given key.
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
	 * Gets the assets by source.
	 *
	 * @param key
	 *            The key.
	 * @return A list of assets whose source matches the key.
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
	 *            The key.
	 * @return A list of assets whose start matches the key.
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
	 * Gets the asset whose given column matches the given key.
	 *
	 * @param col
	 *            The column name to check.
	 * @param key
	 *            The key.
	 * @return A list of assets.
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
	 * Gets all the assets in the database.
	 *
	 * @return A list of all assets.
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
	 * Creates an Asset object. This method parses the data in the ResultSet to
	 * create the entity. It will also call upon Format's findById method to add
	 * the Format.
	 *
	 * @param rs
	 *            The ResultSet containing the information of the Asset.
	 * @return The created asset.
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
