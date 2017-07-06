package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}
