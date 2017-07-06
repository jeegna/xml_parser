package com.esf.xmlParser.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esf.xmlParser.entities.Format;

public class FormatController {

	private DatabaseConnector db;

	public FormatController(String name) {
		db = new DatabaseConnector(name);
	}

	public void addFormats(List<Format> formats) throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO FORMATS VALUES (?, ?, ?, ?, ?);");
		for (Format format : formats) {
			ps.setString(1, format.getId());
			ps.setString(2, format.getName());
			ps.setInt(3, format.getWidth());
			ps.setInt(4, format.getHeight());
			ps.setString(5, format.getFrameDuration());
			ps.addBatch();
		}

		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);

		conn.close();
	}

	public List<Format> getFormats() throws SQLException, ClassNotFoundException {
		List<Format> formats = new ArrayList<Format>();

		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT * FROM FORMATS;");
		while (rs.next()) {
			formats.add(createFormat(rs));
		}

		rs.close();
		conn.close();

		return formats;
	}

	public Format getFormat(String id) throws SQLException, ClassNotFoundException {
		Format format = null;

		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM FORMATS WHERE FORMAT.id=?;");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			format = createFormat(rs);
		}

		rs.close();
		conn.close();

		return format;
	}
	
	private Format createFormat(ResultSet rs) throws SQLException {
		Format format = new Format();

		format.setId(rs.getString("id"));
		format.setName(rs.getString("name"));
		format.setWidth(rs.getInt("width"));
		format.setHeight(rs.getInt("height"));
		format.setFrameDuration(rs.getString("frameDuration"));

		return format;
	}
}
