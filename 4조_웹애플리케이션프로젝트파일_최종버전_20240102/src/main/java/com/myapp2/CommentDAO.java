package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class CommentDAO {
	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	static {
		try (InputStream input = LoginServlet.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			if (input != null) {
				Properties properties = new Properties();
				properties.load(input);
				JDBC_URL = properties.getProperty("jdbc.url");
				JDBC_USER = properties.getProperty("jdbc.user");
				JDBC_PASSWORD = properties.getProperty("jdbc.password");
			} else {

				System.err.println("Error loading " + PROPERTIES_FILE + ". Make sure the file is in the classpath.");
			}
		} catch (IOException e) {

			System.err.println("IOException while loading " + PROPERTIES_FILE + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean addComment(String nickname, int postId, String commentContent) {

		try {

			Class.forName("org.mariadb.jdbc.Driver");
			try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
				String sql = "INSERT INTO comment_table (nickname, comment, wr_time, board_op) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, nickname);
					statement.setString(2, commentContent);
					statement.setInt(3, postId);

					int rowsAffected = statement.executeUpdate();
					return rowsAffected > 0;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Exception during adding comment: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
