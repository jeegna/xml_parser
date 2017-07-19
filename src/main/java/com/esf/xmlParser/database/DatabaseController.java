package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.esf.xmlParser.controllers.AssetClipController;
import com.esf.xmlParser.controllers.AssetController;
import com.esf.xmlParser.controllers.AudioController;
import com.esf.xmlParser.controllers.ClipController;
import com.esf.xmlParser.controllers.EffectController;
import com.esf.xmlParser.controllers.FormatController;
import com.esf.xmlParser.controllers.VideoController;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

/**
 * The DatabaseController class. This class offers methods for interacting with
 * and creating the database.
 */
public class DatabaseController {

	// Database table names
	public static final String ASSET_CLIPS = "ASSET_CLIPS";
	public static final String AUDIOS = "AUDIOS";
	public static final String CLIPS = "CLIPS";
	public static final String VIDEOS = "VIDEOS";
	public static final String EFFECTS = "EFFECTS";
	public static final String ASSETS = "ASSETS";
	public static final String FORMATS = "FORMATS";

	// Database column names
	public static final String ID = "id";
	public static final String ASSET_ID = "assetId";
	public static final String FORMAT_ID = "formatId";
	public static final String NAME = "name";
	public static final String LANE = "lane";
	public static final String START = "start";
	public static final String DURATION = "duration";
	public static final String SOURCE = "src";
	public static final String ROLE = "role";
	public static final String SOURCE_CHANNEL = "srcCh";
	public static final String SOURCE_ID = "srcId";
	public static final String HAS_AUDIO = "hasAudio";
	public static final String HAS_VIDEO = "hasVideo";
	public static final String FORMAT_NAME = "formatName";
	public static final String TC_FORMAT = "tcFormat";
	public static final String UID = "uid";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String FRAME_RATE = "frameRate";
	public static final String OFFSET = "offset";
	public static final String AUDIO_SOURCES = "audioSources";
	public static final String AUDIO_CHANNELS = "audioChannels";
	public static final String AUDIO_RATE = "audioRate";

	private String dbName;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Instantiates a new database controller.
	 *
	 * @param name
	 *            The name of the SQLite database that should be created.
	 */
	public DatabaseController(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		String suffix = ".db";
		if (!name.endsWith(suffix)) {
			name += suffix;
		}

		String prefix = "jdbc:sqlite:";
		if (!name.startsWith(prefix)) {
			name = prefix + name;
		}

		this.dbName = name;
	}

	/**
	 * Creates a connection to an SQLite database.
	 *
	 * @return The connection.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(dbName);
	}

	/**
	 * Populates the database with the entities in the given lists.
	 *
	 * @param assets
	 *            The list of assets
	 * @param assetClips
	 *            The list of asset clips
	 * @param audios
	 *            The list of audios
	 * @param clips
	 *            The list of clips
	 * @param effects
	 *            The list of effects
	 * @param formats
	 *            The list of formats
	 * @param videos
	 *            The list of videos
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void populateDatabase(List<Asset> assets, List<AssetClip> assetClips, List<Audio> audios, List<Clip> clips,
			List<Effect> effects, List<Format> formats, List<Video> videos)
			throws SQLException, ClassNotFoundException {

		createDatabaseTables();

		addFormats(formats);
		addEffects(effects);
		addAssets(assets);
		addAssetClips(assetClips);
		addAudios(audios);
		addVideos(videos);
		addClips(clips);
	}

	/**
	 * Adds the assets.
	 *
	 * @param assets
	 *            The assets to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addAssets(List<Asset> assets) throws ClassNotFoundException, SQLException {
		AssetController assetController = new AssetController(this);
		assetController.addAssets(assets);
	}

	/**
	 * Adds the asset clips.
	 *
	 * @param assetClips
	 *            The asset clips to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addAssetClips(List<AssetClip> assetClips) throws ClassNotFoundException, SQLException {
		AssetClipController assetClipController = new AssetClipController(this);
		assetClipController.addAssetClips(assetClips);
	}

	/**
	 * Adds the audios.
	 *
	 * @param audios
	 *            The audios to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addAudios(List<Audio> audios) throws ClassNotFoundException, SQLException {
		AudioController audioController = new AudioController(this);
		audioController.addAudios(audios);
	}

	/**
	 * Adds the clips.
	 *
	 * @param clips
	 *            The clips to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addClips(List<Clip> clips) throws ClassNotFoundException, SQLException {
		ClipController clipController = new ClipController(this);
		clipController.addClips(clips);
	}

	/**
	 * Adds the effects.
	 *
	 * @param effects
	 *            The effects to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addEffects(List<Effect> effects) throws ClassNotFoundException, SQLException {
		EffectController effectController = new EffectController(this);
		effectController.addEffects(effects);
	}

	/**
	 * Adds the formats.
	 *
	 * @param formats
	 *            The formats to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addFormats(List<Format> formats) throws ClassNotFoundException, SQLException {
		FormatController formatController = new FormatController(this);
		formatController.addFormats(formats);
	}

	/**
	 * Adds the videos.
	 *
	 * @param videos
	 *            The videos to add.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public void addVideos(List<Video> videos) throws ClassNotFoundException, SQLException {
		VideoController videoController = new VideoController(this);
		videoController.addVideos(videos);
	}

	/**
	 * Gets the assets whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of assets matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Asset> getAssetsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		AssetController assetController = new AssetController(this);
		List<Asset> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(assetController.getAssetsById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(assetController.getAssetsByName(key));
				break;
			case DatabaseController.DURATION:
				list.addAll(assetController.getAssetsByDuration(key));
				break;
			case DatabaseController.HAS_AUDIO:
				list.addAll(assetController.getAssetsByHasAudio(key));
				break;
			case DatabaseController.HAS_VIDEO:
				list.addAll(assetController.getAssetsByHasVideo(key));
				break;
			case DatabaseController.SOURCE:
				list.addAll(assetController.getAssetsBySrc(key));
				break;
			case DatabaseController.START:
				list.addAll(assetController.getAssetsByStart(key));
				break;
			case DatabaseController.UID:
				list.addAll(assetController.getAssetsByUID(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the asset clips whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of asset clips matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<AssetClip> getAssetClipsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		AssetClipController assetClipController = new AssetClipController(this);
		List<AssetClip> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(assetClipController.getAssetClipsById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(assetClipController.getAssetClipsByName(key));
				break;
			case DatabaseController.LANE:
				list.addAll(assetClipController.getAssetClipsByDuration(key));
				break;
			case DatabaseController.OFFSET:
				list.addAll(assetClipController.getAssetClipsByOffset(key));
				break;
			case DatabaseController.DURATION:
				list.addAll(assetClipController.getAssetClipsByDuration(key));
				break;
			case DatabaseController.START:
				list.addAll(assetClipController.getAssetClipsByStart(key));
				break;
			case DatabaseController.ROLE:
				list.addAll(assetClipController.getAssetClipsByStart(key));
				break;
			case DatabaseController.TC_FORMAT:
				list.addAll(assetClipController.getAssetClipsByTcFormat(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the audios whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of audios matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Audio> getAudiosFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		AudioController audioController = new AudioController(this);
		List<Audio> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(audioController.getAudiosById(key));
				break;
			case DatabaseController.LANE:
				list.addAll(audioController.getAudiosByLane(key));
				break;
			case DatabaseController.ROLE:
				list.addAll(audioController.getAudiosByRole(key));
				break;
			case DatabaseController.OFFSET:
				list.addAll(audioController.getAudiosByOffset(key));
				break;
			case DatabaseController.DURATION:
				list.addAll(audioController.getAudiosByDuration(key));
				break;
			case DatabaseController.START:
				list.addAll(audioController.getAudiosByStart(key));
				break;
			case DatabaseController.SOURCE_CHANNEL:
				list.addAll(audioController.getAudiosBySrcCh(key));
				break;
			case DatabaseController.SOURCE_ID:
				list.addAll(audioController.getAudiosBySrcId(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the clips whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of clips matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Clip> getClipsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		ClipController clipController = new ClipController(this);
		List<Clip> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(clipController.getClipsById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(clipController.getClipsByName(key));
				break;
			case DatabaseController.OFFSET:
				list.addAll(clipController.getClipsByOffset(key));
				break;
			case DatabaseController.DURATION:
				list.addAll(clipController.getClipsByDuration(key));
				break;
			case DatabaseController.START:
				list.addAll(clipController.getClipsByStart(key));
				break;
			case DatabaseController.TC_FORMAT:
				list.addAll(clipController.getClipsByTcFormat(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the effects whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of effects matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Effect> getEffectsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		EffectController effectController = new EffectController(this);
		List<Effect> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(effectController.getEffectsById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(effectController.getEffectsByName(key));
				break;
			case DatabaseController.UID:
				list.addAll(effectController.getEffectsByUid(key));
				break;
			case DatabaseController.SOURCE:
				list.addAll(effectController.getEffectsBySrc(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the formats whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of formats matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Format> getFormatsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		FormatController formatController = new FormatController(this);
		List<Format> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(formatController.getFormatsById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(formatController.getFormatsByName(key));
				break;
			case DatabaseController.WIDTH:
				list.addAll(formatController.getFormatsByWidth(key));
				break;
			case DatabaseController.HEIGHT:
				list.addAll(formatController.getFormatsByHeight(key));
				break;
			case DatabaseController.FRAME_RATE:
				list.addAll(formatController.getFormatsByFrameRate(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the videos whose column fields match the given key.
	 *
	 * @param columns
	 *            The column names.
	 * @param key
	 *            The key with which to match the column fields.
	 * @return A list of videos matching the criteria.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Video> getVideosFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
		VideoController videoController = new VideoController(this);
		List<Video> list = new ArrayList<>();
		for (String col : columns) {
			switch (col) {
			case DatabaseController.ID:
				list.addAll(videoController.getVideosById(key));
				break;
			case DatabaseController.NAME:
				list.addAll(videoController.getVideosByName(key));
				break;
			case DatabaseController.LANE:
				list.addAll(videoController.getVideosByLane(key));
				break;
			case DatabaseController.OFFSET:
				list.addAll(videoController.getVideosByOffset(key));
				break;
			case DatabaseController.DURATION:
				list.addAll(videoController.getVideosByDuration(key));
				break;
			case DatabaseController.START:
				list.addAll(videoController.getVideosByStart(key));
				break;
			}
		}
		return list;
	}

	/**
	 * Gets the assets.
	 *
	 * @return The assets.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Asset> getAssets() throws ClassNotFoundException, SQLException {
		AssetController assetController = new AssetController(this);
		return assetController.getAssets();
	}

	/**
	 * Gets the assets by id.
	 *
	 * @param id
	 *            The id.
	 * @return The assets whose id field matches the given id.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Asset> getAssetsById(String id) throws ClassNotFoundException, SQLException {
		AssetController assetController = new AssetController(this);
		return assetController.getAssetsById(id);
	}

	/**
	 * Gets the asset clips.
	 *
	 * @return The asset clips.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<AssetClip> getAssetClips() throws ClassNotFoundException, SQLException {
		AssetClipController assetClipController = new AssetClipController(this);
		return assetClipController.getAssetClips();
	}

	/**
	 * Gets the audios.
	 *
	 * @return The audios.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Audio> getAudios() throws ClassNotFoundException, SQLException {
		AudioController audioController = new AudioController(this);
		return audioController.getAudios();
	}

	/**
	 * Gets the clips.
	 *
	 * @return The clips.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Clip> getClips() throws ClassNotFoundException, SQLException {
		ClipController clipController = new ClipController(this);
		return clipController.getClips();
	}

	/**
	 * Gets the effects.
	 *
	 * @return The effects.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Effect> getEffects() throws ClassNotFoundException, SQLException {
		EffectController effectController = new EffectController(this);
		return effectController.getEffects();
	}

	/**
	 * Gets the effects by id.
	 *
	 * @param id
	 *            The id.
	 * @return The effects whose id field matches the given id.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Effect> getEffectsById(String id) throws ClassNotFoundException, SQLException {
		EffectController effectController = new EffectController(this);
		return effectController.getEffectsById(id);
	}

	/**
	 * Gets the formats.
	 *
	 * @return The formats.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Format> getFormats() throws ClassNotFoundException, SQLException {
		FormatController formatController = new FormatController(this);
		return formatController.getFormats();
	}

	/**
	 * Gets the formats by id.
	 *
	 * @param id
	 *            The id.
	 * @return The formats whose id field matches the given id.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Format> getFormatsById(String id) throws ClassNotFoundException, SQLException {
		FormatController formatController = new FormatController(this);
		return formatController.getFormatsById(id);
	}

	/**
	 * Gets the videos.
	 *
	 * @return The videos.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 */
	public List<Video> getVideos() throws ClassNotFoundException, SQLException {
		VideoController videoController = new VideoController(this);
		return videoController.getVideos();
	}

	/**
	 * Creates the database tables.
	 *
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private void createDatabaseTables() throws SQLException, ClassNotFoundException {
		logger.info("Creating database tables...");

		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("DROP TABLE IF EXISTS " + VIDEOS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + CLIPS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + AUDIOS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + ASSET_CLIPS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + EFFECTS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + ASSETS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + FORMATS + ";");

		stmt.executeUpdate(
				"CREATE TABLE " + FORMATS + " (" +
					ID + " TEXT PRIMARY KEY," +
					NAME + " TEXT," +
					WIDTH + " INTEGER," +
					HEIGHT + " INTEGER," +
					FRAME_RATE + " TEXT" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + ASSETS + " (" +
					ID + " TEXT PRIMARY KEY," +
					DURATION + " TEXT," +
					HAS_AUDIO + " INTEGER CHECK (" + HAS_AUDIO + " IN (0,1))," + 
					HAS_VIDEO + " INTEGER CHECK (" + HAS_VIDEO + " IN (0,1))," + 
					NAME + " TEXT," +
					UID + " TEXT," +
					SOURCE + " TEXT," +
					START + " TEXT," +
					FORMAT_ID + " TEXT," +
					AUDIO_SOURCES + " TEXT," +
					AUDIO_CHANNELS + " TEXT," +
					AUDIO_RATE + " TEXT," +
					"FOREIGN KEY(" + FORMAT_ID + ") REFERENCES " + FORMATS + "(" + ID + ")" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + EFFECTS + " (" +
					ID + " TEXT PRIMARY KEY," + 
					NAME + " TEXT," +
					UID + " TEXT," +
					SOURCE + " TEXT" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + ASSET_CLIPS + " (" +
					ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					ASSET_ID + " TEXT," +
					NAME + " TEXT," +
					LANE + " INTEGER," +
					OFFSET + " TEXT," +
					DURATION + " TEXT," +
					START + " TEXT," +
					ROLE + " TEXT," +
					FORMAT_ID + " TEXT," +
					TC_FORMAT + " TEXT," +
					"FOREIGN KEY(" + ASSET_ID + ") REFERENCES " + ASSETS + "(" + ID + ")," +
					"FOREIGN KEY(" + FORMAT_ID + ") REFERENCES " + FORMATS + "(" + ID + ")" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + AUDIOS + " (" +
					ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					ASSET_ID + " TEXT," +
					NAME + " TEXT," +
					LANE + " INTEGER," +
					ROLE + " TEXT," +
					OFFSET + " TEXT," +
					DURATION + " TEXT," +
					START + " TEXT," +
					SOURCE_CHANNEL + " TEXT," +
					SOURCE_ID + " INTEGER," +
					TC_FORMAT + " TEXT," +
					"FOREIGN KEY(" + ASSET_ID + ") REFERENCES " + ASSETS + "(" + ID + ")" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + CLIPS + " (" +
					ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					NAME + " TEXT," +
					OFFSET + " TEXT," +
					DURATION + " TEXT," +
					START + " TEXT," +
					TC_FORMAT + " TEXT" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE " + VIDEOS + " (" +
					ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					NAME + " TEXT," +
					LANE + " INTEGER," +
					OFFSET + " TEXT," +
					ASSET_ID + " TEXT," +
					DURATION + " TEXT," +
					START + " TEXT," +
					TC_FORMAT + " TEXT," +
					"FOREIGN KEY(" + ASSET_ID + ") REFERENCES " + ASSETS + "(" + ID + ")" +
				");"
		);

		stmt.close();
		conn.close();
	}
}
