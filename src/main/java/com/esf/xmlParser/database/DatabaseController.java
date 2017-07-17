package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(dbName);
	}

	/**
	 * @param assets
	 * @param assetClips
	 * @param audios
	 * @param clips
	 * @param effects
	 * @param formats
	 * @param videos
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
	 * @param assets
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addAssets(List<Asset> assets) throws ClassNotFoundException, SQLException {
		assetController.addAssets(assets);
	}

	/**
	 * @param assetClips
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addAssetClips(List<AssetClip> assetClips) throws ClassNotFoundException, SQLException {
		assetClipController.addAssetClips(assetClips);
	}

	/**
	 * @param audios
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addAudios(List<Audio> audios) throws ClassNotFoundException, SQLException {
		audioController.addAudios(audios);
	}

	/**
	 * @param clips
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addClips(List<Clip> clips) throws ClassNotFoundException, SQLException {
		clipController.addClips(clips);
	}

	/**
	 * @param effects
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addEffects(List<Effect> effects) throws ClassNotFoundException, SQLException {
		effectController.addEffects(effects);
	}

	/**
	 * @param formats
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addFormats(List<Format> formats) throws ClassNotFoundException, SQLException {
		formatController.addFormats(formats);
	}

	/**
	 * @param videos
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void addVideos(List<Video> videos) throws ClassNotFoundException, SQLException {
		videoController.addVideos(videos);
	}

	/**
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @param columns
	 * @param key
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosFromQueryInfo(Set<String> columns, String key)
			throws ClassNotFoundException, SQLException {
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
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssets() throws ClassNotFoundException, SQLException {
		return assetController.getAssets();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsById(String id) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByName(String name) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByName(name);
	}

	/**
	 * @param duration
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByDuration(duration);
	}

	/**
	 * @param hasAudio
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByHasAudio(String hasAudio) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByHasAudio(hasAudio);
	}

	/**
	 * @param hasVideo
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByHasVideo(String hasVideo) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByHasVideo(hasVideo);
	}

	/**
	 * @param src
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsBySrc(String src) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsBySrc(src);
	}

	/**
	 * @param start
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByStart(String start) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByStart(start);
	}

	/**
	 * @param uid
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Asset> getAssetsByUID(String uid) throws ClassNotFoundException, SQLException {
		return assetController.getAssetsByUID(uid);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClips() throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClips();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsById(String id) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByName(String name) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByName(name);
	}

	/**
	 * @param lane
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByLane(String lane) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByLane(lane);
	}

	/**
	 * @param offset
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByOffset(String offset) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByOffset(offset);
	}

	/**
	 * @param duration
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByDuration(duration);
	}

	/**
	 * @param start
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByStart(String start) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByStart(start);
	}

	/**
	 * @param role
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByRole(String role) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByRole(role);
	}

	/**
	 * @param tcFormat
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<AssetClip> getAssetClipsByTcFormat(String tcFormat) throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClipsByTcFormat(tcFormat);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudios() throws ClassNotFoundException, SQLException {
		return audioController.getAudios();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosById(String id) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosById(id);
	}

	/**
	 * @param lane
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosByLane(String lane) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByLane(lane);
	}

	/**
	 * @param role
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosByRole(String role) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByRole(role);
	}

	/**
	 * @param offset
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosByOffset(String offset) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByOffset(offset);
	}

	/**
	 * @param duration
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosByDuration(String duration) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByDuration(duration);
	}

	/**
	 * @param start
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosByStart(String start) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosByStart(start);
	}

	/**
	 * @param srcCh
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosBySrcCh(String srcCh) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosBySrcCh(srcCh);
	}

	/**
	 * @param srcId
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Audio> getAudiosBySrcId(String srcId) throws ClassNotFoundException, SQLException {
		return audioController.getAudiosBySrcId(srcId);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClips() throws ClassNotFoundException, SQLException {
		return clipController.getClips();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsById(String id) throws ClassNotFoundException, SQLException {
		return clipController.getClipsById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByName(String name) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByName(name);
	}

	/**
	 * @param offset
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByOffset(String offset) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByOffset(offset);
	}

	/**
	 * @param duration
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByDuration(String duration) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByDuration(duration);
	}

	/**
	 * @param start
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByStart(String start) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByStart(start);
	}

	/**
	 * @param tcFormat
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Clip> getClipsByTcFormat(String tcFormat) throws ClassNotFoundException, SQLException {
		return clipController.getClipsByTcFormat(tcFormat);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffects() throws ClassNotFoundException, SQLException {
		return effectController.getEffects();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsById(String id) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsByName(String name) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsByName(name);
	}

	/**
	 * @param uid
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsByUid(String uid) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsByUid(uid);
	}

	/**
	 * @param src
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Effect> getEffectsBySrc(String src) throws ClassNotFoundException, SQLException {
		return effectController.getEffectsBySrc(src);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormats() throws ClassNotFoundException, SQLException {
		return formatController.getFormats();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsById(String id) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByName(String name) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByName(name);
	}

	/**
	 * @param width
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByWidth(String width) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByWidth(width);
	}

	/**
	 * @param height
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByHeight(String height) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByHeight(height);
	}

	/**
	 * @param fps
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Format> getFormatsByFrameRate(String fps) throws ClassNotFoundException, SQLException {
		return formatController.getFormatsByFrameRate(fps);
	}

	/**
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideos() throws ClassNotFoundException, SQLException {
		return videoController.getVideos();
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosById(String id) throws ClassNotFoundException, SQLException {
		return videoController.getVideosById(id);
	}

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByName(String name) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByName(name);
	}

	/**
	 * @param lane
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByLane(String lane) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByLane(lane);
	}

	/**
	 * @param offset
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByOffset(String offset) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByOffset(offset);
	}

	/**
	 * @param duration
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByDuration(String duration) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByDuration(duration);
	}

	/**
	 * @param start
	 * @return
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public List<Video> getVideosByStart(String start) throws ClassNotFoundException, SQLException {
		return videoController.getVideosByStart(start);
	}

	/**
	 * @param queries
	 * @param key
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	public void executeQueries(List<String> queries, String key) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();

		for (String query : queries) {
			logger.info("Running query: " + query.replaceAll("[?]", "'" + key + "'"));
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, key);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
					System.out.println(rs.getString(i));
				}
			}

			ps.close();
			rs.close();
		}

		conn.close();
	}

	/**
	 * @throws SQLException
	 *             If an SQL Exception occurs.
	 * @throws ClassNotFoundException
	 *             If the SQLite JDBC driver was not found.
	 */
	private void createDatabaseTables() throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("DROP TABLE IF EXISTS " + VIDEOS+ ";");
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
