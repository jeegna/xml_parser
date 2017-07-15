package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

	private AssetClipController assetClipController;
	private AssetController assetController;
	private AudioController audioController;
	private ClipController clipController;
	private EffectController effectController;
	private FormatController formatController;
	private VideoController videoController;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
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
		assetClipController = new AssetClipController(this);
		assetController = new AssetController(this);
		audioController = new AudioController(this);
		clipController = new ClipController(this);
		effectController = new EffectController(this);
		formatController = new FormatController(this);
		videoController = new VideoController(this);
	}

	/**
	 * Creates a connection to an SQLite database
	 * 
	 * @param dbName
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(dbName);
	}

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

	public void addAssets(List<Asset> assets) throws ClassNotFoundException, SQLException {
		assetController.addAssets(assets);
	}

	public void addAssetClips(List<AssetClip> assetClips) throws ClassNotFoundException, SQLException {
		assetClipController.addAssetClips(assetClips);
	}

	public void addAudios(List<Audio> audios) throws ClassNotFoundException, SQLException {
		audioController.addAudios(audios);
	}

	public void addClips(List<Clip> clips) throws ClassNotFoundException, SQLException {
		clipController.addClips(clips);
	}

	public void addEffects(List<Effect> effects) throws ClassNotFoundException, SQLException {
		effectController.addEffects(effects);
	}

	public void addFormats(List<Format> formats) throws ClassNotFoundException, SQLException {
		formatController.addFormats(formats);
	}

	public void addVideos(List<Video> videos) throws ClassNotFoundException, SQLException {
		videoController.addVideos(videos);
	}

	public List<Asset> getAssets() throws ClassNotFoundException, SQLException {
		return assetController.getAssets();
	}

	public Asset getAssetById(String id) throws ClassNotFoundException, SQLException {
		return assetController.getAssetById(id);
	}

	public List<Asset> getAssetsByName(String name) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByName(name);
	}

	public List<Asset> getAssetsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByDuration(duration);
	}

	public List<Asset> getAssetsByHasAudio(boolean hasAudio) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByHasAudio(hasAudio);
	}

	public List<Asset> getAssetsByHasVideo(boolean hasVideo) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByHasVideo(hasVideo);
	}

	public List<Asset> getAssetsBySrc(String src) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsBySrc(src);
	}

	public List<Asset> getAssetsByStart(String start) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByStart(start);
	}

	public List<Asset> getAssetsByUID(String uid) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByUID(uid);
	}

	public List<AssetClip> getAssetClips() throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClips();
	}

	public AssetClip getAssetClipById(int id) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipById(id);
	}

	public List<AssetClip> getAssetClipsByName(String name) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByName(name);
	}

	public List<AssetClip> getAssetClipsByLane(int lane) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByLane(lane);
	}

	public List<AssetClip> getAssetClipsByOffset(String offset) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByOffset(offset);
	}

	public List<AssetClip> getAssetClipsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByDuration(duration);
	}

	public List<AssetClip> getAssetClipsByStart(String start) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByStart(start);
	}

	public List<AssetClip> getAssetClipsByRole(String role) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByRole(role);
	}

	public List<Audio> getAudios() throws ClassNotFoundException, SQLException {
		return audioController.getAudios();
	}

	public Audio getAudioById(int id) throws ClassNotFoundException, SQLException {
		return audioController.getAudioById(id);
	}

	public List<Audio> getAudiosByLane(int lane) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByLane(lane);
	}

	public List<Audio> getAudiosByRole(String role) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByRole(role);
	}

	public List<Audio> getAudiosByOffset(String offset) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByOffset(offset);
	}

	public List<Audio> getAudiosByDuration(String duration) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByDuration(duration);
	}

	public List<Audio> getAudiosByStart(String start) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByStart(start);
	}

	public List<Audio> getAudiosBySrcCh(String srcCh) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosBySrcCh(srcCh);
	}

	public List<Audio> getAudiosBySrcId(int srcId) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosBySrcId(srcId);
	}

	public List<Clip> getClips() throws ClassNotFoundException, SQLException {
		return clipController.getClips();
	}

	public Clip getClipById(int id) throws ClassNotFoundException, SQLException {
		return clipController.getClipById(id);
	}

	public List<Clip> getClipsByName(String name) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByName(name);
	}

	public List<Clip> getClipsByOffset(String offset) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByOffset(offset);
	}

	public List<Clip> getClipsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByDuration(duration);
	}

	public List<Clip> getClipsByStart(String start) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByStart(start);
	}

	public List<Clip> getClipsByTcFormat(String tcFormat) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByTcFormat(tcFormat);
	}

	public List<Effect> getEffects() throws ClassNotFoundException, SQLException {
		return effectController.getEffects();
	}

	public Effect getEffectById(String id) throws ClassNotFoundException, SQLException {
		return effectController.getEffectById(id);
	}

	public List<Effect> getEffectsByName(String name) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsByName(name);
	}

	public List<Effect> getEffectsByUid(String uid) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsByUid(uid);
	}

	public List<Effect> getEffectsBySrc(String src) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsBySrc(src);
	}

	public List<Format> getFormats() throws ClassNotFoundException, SQLException {
		return formatController.getFormats();
	}

	public Format getFormatById(String id) throws ClassNotFoundException, SQLException {
		return formatController.getFormatById(id);
	}

	public List<Format> getFormatsByName(String name) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByName(name);
	}

	public List<Format> getFormatsByWidth(int width) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByWidth(width);
	}

	public List<Format> getFormatsByHeight(int height) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByHeight(height);
	}

	public List<Format> getFormatsByFrameRate(String fps) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByFrameRate(fps);
	}

	public List<Video> getVideos() throws ClassNotFoundException, SQLException {
		return videoController.getVideos();
	}

	public Video getVideoById(int id) throws ClassNotFoundException, SQLException {
		return videoController.getVideoById(id);
	}

	public List<Video> getVideosByName(String name) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByName(name);
	}

	public List<Video> getVideosByLane(int lane) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByLane(lane);
	}

	public List<Video> getVideosByOffset(String offset) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByOffset(offset);
	}

	public List<Video> getVideosByDuration(String duration) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByDuration(duration);
	}

	public List<Video> getVideosByStart(String start) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByStart(start);
	}

	public void executeQueries(List<String> queries, String key) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();

		for (String query : queries) {
			logger.info("Running query: " + query.replaceAll("[?]", "'" + key + "'"));
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, key);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
					System.out.println(rs.getString(i));
				}
			}

			ps.close();
			rs.close();
		}

		conn.close();
	}

	private void createDatabaseTables() throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("DROP TABLE IF EXISTS " + ASSET_CLIPS+ ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + ASSET_CLIPS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + AUDIOS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + CLIPS + ";");
		stmt.executeUpdate("DROP TABLE IF EXISTS " + VIDEOS + ";");
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
				"CREATE TABLE ASSET_" + CLIPS + " (" +
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
					LANE + " INTEGER," +
					ROLE + " TEXT," +
					OFFSET + " TEXT," +
					DURATION + " TEXT," +
					START + " TEXT," +
					SOURCE_CHANNEL + " TEXT," +
					SOURCE_ID + " INTEGER," +
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
					"FOREIGN KEY(" + ASSET_ID + ") REFERENCES " + ASSETS + "(" + ID + ")" +
				");"
		);

		stmt.close();
		conn.close();
	}
}
