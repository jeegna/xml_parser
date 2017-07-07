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

public class DatabaseConnector {

	private String name;

	/**
	 * 
	 * @param name
	 */
	public DatabaseConnector(String name) {
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

	public void createDatabaseTables() throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DROP TABLE IF EXISTS ASSETS;");
		stmt.executeUpdate("CREATE TABLE ASSETS (id, duration, hasVideo, hasAudio, name, uid, src, start, formatId, audioSources, audioChannels, audioRate);");
		stmt.executeUpdate("DROP TABLE IF EXISTS ASSET_CLIPS;");
		stmt.executeUpdate("CREATE TABLE ASSET_CLIPS (id, assetId, name, lane, offset, duration, start, role, formatId, tcFormat);");
		stmt.executeUpdate("DROP TABLE IF EXISTS AUDIOS;");
		stmt.executeUpdate("CREATE TABLE AUDIOS (id, assetId, lane, role, offset, duration, start, srcCh, srcId);");
		stmt.executeUpdate("DROP TABLE IF EXISTS CLIPS;");
		stmt.executeUpdate("CREATE TABLE CLIPS (id, name, offset, duration, start, tcFormat);");
		stmt.executeUpdate("DROP TABLE IF EXISTS EFFECTS;");
		stmt.executeUpdate("CREATE TABLE EFFECTS (id, name, uid, src);");
		stmt.executeUpdate("DROP TABLE IF EXISTS FORMATS;");
		stmt.executeUpdate("CREATE TABLE FORMATS (id, name, width, height, frameDuration);");
		stmt.executeUpdate("DROP TABLE IF EXISTS VIDEOS;");
		stmt.executeUpdate("CREATE TABLE VIDEOS (id, name, lane, offset, assetId, duration, start);");
		
		stmt.close();
		conn.close();
	}
	
	private void createDatabase(String fileName) throws SQLException, ClassNotFoundException {
//		List<Format> formats = getFormats();
//		List<Effect> effects = getEffects();
//		List<Asset> assets = getAssets();
//		List<AssetClip> assetClips = getAssetClips();
//		List<Audio> audios = getAudios();
//		List<Video> videos = getVideos();
//		List<Clip> clips = getClips();
//
//		DatabaseConnector db = new DatabaseConnector(fileName);
//		AssetClipController assetClipController = new AssetClipController(fileName);
//		AssetController assetController = new AssetController(fileName);
//		AudioController audioController = new AudioController(fileName);
//		ClipController clipController = new ClipController(fileName);
//		EffectController effectController = new EffectController(fileName);
//		FormatController formatController = new FormatController(fileName);
//		VideoController videoController = new VideoController(fileName);
//
//		db.createDatabaseTables();
//		formatController.addFormats(formats);
//		effectController.addEffects(effects);
//		assetController.addAssets(assets);
//		assetClipController.addAssetClips(assetClips);
//		audioController.addAudios(audios);
//		videoController.addVideos(videos);
//		clipController.addClips(clips);
	}
}
