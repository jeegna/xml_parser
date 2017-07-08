package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

	private String name;

	private AssetClipController assetClipController;
	private AssetController assetController;
	private AudioController audioController;
	private ClipController clipController;
	private EffectController effectController;
	private FormatController formatController;
	private VideoController videoController;

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

		this.name = name;
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
	 * @param name
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(name);
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

	public List<AssetClip> getAssetClips() throws ClassNotFoundException, SQLException {
		return assetClipController.getAssetClips();
	}

	public List<Audio> getAudios() throws ClassNotFoundException, SQLException {
		return audioController.getAudios();
	}

	public List<Clip> getClips() throws ClassNotFoundException, SQLException {
		return clipController.getClips();
	}

	public List<Effect> getEffects() throws ClassNotFoundException, SQLException {
		return effectController.getEffects();
	}

	public List<Format> getFormats() throws ClassNotFoundException, SQLException {
		return formatController.getFormats();
	}

	public List<Video> getVideos() throws ClassNotFoundException, SQLException {
		return videoController.getVideos();
	}

	private void createDatabaseTables() throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("DROP TABLE IF EXISTS ASSET_CLIPS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS AUDIOS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS CLIPS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS VIDEOS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS EFFECTS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS ASSETS;");
		stmt.executeUpdate("DROP TABLE IF EXISTS FORMATS;");

		stmt.executeUpdate(
				"CREATE TABLE FORMATS (" +
					"id TEXT PRIMARY KEY," +
					"name TEXT DEFAULT ''," +
					"width INTEGER DEFAULT 0," +
					"height INTEGER DEFAULT 0," +
					"frameDuration TEXT DEFAULT ''" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE ASSETS (" +
					"id TEXT PRIMARY KEY," +
					"duration TEXT DEFAULT ''," +
					"hasVideo INTEGER CHECK (hasVideo IN (0,1))," + 
					"hasAudio INTEGER CHECK (hasVideo IN (0,1))," +
					"name TEXT DEFAULT ''," +
					"uid TEXT DEFAULT ''," +
					"src TEXT DEFAULT ''," +
					"start TEXT DEFAULT '0s'," +
					"formatId TEXT DEFAULT ''," +
					"audioSources TEXT DEFAULT ''," +
					"audioChannels TEXT DEFAULT ''," +
					"audioRate TEXT DEFAULT ''," +
					"FOREIGN KEY(formatId) REFERENCES FORMATS(id)" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE EFFECTS (" +
					"id TEXT PRIMARY KEY," + 
					"name TEXT DEFAULT ''," +
					"uid TEXT DEFAULT ''," +
					"src TEXT DEFAULT ''" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE ASSET_CLIPS (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"assetId TEXT DEFAULT ''," +
					"name TEXT DEFAULT ''," +
					"lane INTEGER DEFAULT 0," +
					"offset TEXT  DEFAULT '0s'," +
					"duration TEXT DEFAULT '0s'," +
					"start TEXT DEFAULT '0s'," +
					"role TEXT DEFAULT ''," +
					"formatId TEXT DEFAULT ''," +
					"tcFormat TEXT DEFAULT ''," +
					"FOREIGN KEY(assetId) REFERENCES ASSETS(id)," +
					"FOREIGN KEY(formatId) REFERENCES FORMATS(id)" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE AUDIOS (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"assetId TEXT DEFAULT ''," +
					"lane INTEGER DEFAULT 0," +
					"role TEXT DEFAULT ''," +
					"offset TEXT DEFAULT '0s'," +
					"duration TEXT DEFAULT '0s'," +
					"start TEXT DEFAULT '0s'," +
					"srcCh TEXT DEFAULT ''," +
					"srcId INTEGER DEFAULT -1," +
					"FOREIGN KEY(assetId) REFERENCES ASSETS(id)" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE CLIPS (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name TEXT DEFAULT ''," +
					"offset TEXT DEFAULT '0s'," +
					"duration TEXT DEFAULT '0s'," +
					"start TEXT DEFAULT '0s'," +
					"tcFormat TEXT DEFAULT ''" +
				");"
		);
		stmt.executeUpdate(
				"CREATE TABLE VIDEOS (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name TEXT DEFAULT ''," +
					"lane INTEGER DEFAULT 0," +
					"offset TEXT DEFAULT '0s'," +
					"assetId TEXT DEFAULT ''," +
					"duration TEXT DEFAULT '0s'," +
					"start TEXT DEFAULT '0s'," +
					"FOREIGN KEY(assetId) REFERENCES ASSETS(id)" +
				");"
		);

		stmt.close();
		conn.close();
	}
}
