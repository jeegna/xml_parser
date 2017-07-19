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

/**
 * The AssetClip controller class. This class offers database access methods for
 * AssetClip entities.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class AssetClipController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DatabaseController db;

	/**
	 * Instantiates a new asset clip controller.
	 * 
	 * @param db
	 *            The database controller to use.
	 */
	public AssetClipController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Adds the asset clips.
	 * 
	 * @param assetClips
	 *            A list of asset clips to add.
	 * @throws SQLException
	 *             If an SQL exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addAssetClips(List<AssetClip> assetClips) throws SQLException, ClassNotFoundException {
		logger.info("Adding AssetClips...");
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO " + DatabaseController.ASSET_CLIPS + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		for (AssetClip assetClip : assetClips) {
			logger.info("Adding " + assetClip);
			ps.setString(1, assetClip.getAsset().getId());
			ps.setString(2, assetClip.getName());
			ps.setInt(3, assetClip.getLane());
			ps.setString(4, assetClip.getOffset());
			ps.setString(5, assetClip.getDuration());
			ps.setString(6, assetClip.getStart());
			ps.setString(7, assetClip.getRole());
			ps.setString(8, assetClip.getFormat().getId());
			ps.setString(9, assetClip.getTcFormat());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		ps.close();
		conn.close();
	}

	/**
	 * Gets all the asset clips in the database.
	 * 
	 * @return A list of all asset clips in the database.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClips() throws SQLException, ClassNotFoundException {
		logger.info("Getting all Asset Clips from database...");
		return getAll();
	}

	/**
	 * Gets the asset clips by id.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose id matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsById(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clip with id " + key);
		return get(DatabaseController.ID, key);
	}

	/**
	 * Gets the asset clips by name.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose name matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByName(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with name like " + key);
		return get(DatabaseController.NAME, key);
	}

	/**
	 * Gets the asset clips by lane.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose lane matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByLane(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with lane like " + key);
		return get(DatabaseController.LANE, key);
	}

	/**
	 * Gets the asset clips by offset.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose offset matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByOffset(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with offset like " + key);
		return get(DatabaseController.OFFSET, key);
	}

	/**
	 * Gets the asset clips by duration.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose duration matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByDuration(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with duration like " + key);
		return get(DatabaseController.DURATION, key);
	}

	/**
	 * Gets the asset clips by start.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose start matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByStart(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with start like " + key);
		return get(DatabaseController.START, key);
	}

	/**
	 * Gets the asset clips by role.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose role matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByRole(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with role like " + key);
		return get(DatabaseController.ROLE, key);
	}

	/**
	 * Gets the asset clips by TC format.
	 *
	 * @param key
	 *            The key.
	 * @return A list of asset clips whose TC format matches the key.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByTcFormat(String key) throws SQLException, ClassNotFoundException {
		logger.info("Getting Asset Clips with TC format like " + key);
		return get(DatabaseController.TC_FORMAT, key);
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
	private List<AssetClip> get(String col, String key) throws SQLException, ClassNotFoundException {
		List<AssetClip> assetClips = new ArrayList<>();

		Connection conn = db.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM " + DatabaseController.ASSET_CLIPS + " WHERE " + col + " LIKE ?;");
		ps.setString(1, key);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			AssetClip assetClip = createAssetClip(rs);
			assetClips.add(assetClip);

			logger.info("Found " + assetClip);
		}

		ps.close();
		rs.close();
		conn.close();

		return assetClips;
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
	private List<AssetClip> getAll() throws SQLException, ClassNotFoundException {
		List<AssetClip> assetClips = new ArrayList<>();

		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseController.ASSET_CLIPS + ";");
		while (rs.next()) {
			AssetClip assetClip = createAssetClip(rs);
			assetClips.add(assetClip);

			logger.info("Found " + assetClip);
		}

		stmt.close();
		rs.close();
		conn.close();

		return assetClips;
	}

	/**
	 * Creates an AssetClip object. This method parses the data in the ResultSet
	 * to create the entity. It will also call upon Asset's and Format's
	 * findById methods to add the Asset and Format.
	 *
	 * @param rs
	 *            The ResultSet containing the information of the AssetClip.
	 * @return The created asset clip.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private AssetClip createAssetClip(ResultSet rs) throws SQLException, ClassNotFoundException {
		logger.info("Creating Asset Clip...");

		AssetClip assetClip = new AssetClip();
		assetClip.setId(rs.getInt(DatabaseController.ID));
		assetClip.setName(rs.getString(DatabaseController.NAME));
		assetClip.setLane(rs.getInt(DatabaseController.LANE));
		assetClip.setOffset(rs.getString(DatabaseController.OFFSET));
		assetClip.setDuration(rs.getString(DatabaseController.DURATION));
		assetClip.setStart(rs.getString(DatabaseController.START));
		assetClip.setRole(rs.getString(DatabaseController.ROLE));
		assetClip.setTcFormat(rs.getString(DatabaseController.TC_FORMAT));

		List<Asset> assets = db.getAssetsById(rs.getString(DatabaseController.ASSET_ID));
		Asset asset = null;
		if (assets != null && !assets.isEmpty()) {
			asset = assets.get(0);
		}
		assetClip.setAsset(asset);

		List<Format> formats = db.getFormatsById(rs.getString(DatabaseController.FORMAT_ID));
		Format format = null;
		if (formats != null && !formats.isEmpty()) {
			format = formats.get(0);
		}
		assetClip.setFormat(format);

		logger.info("Created " + assetClip);
		return assetClip;
	}
}
