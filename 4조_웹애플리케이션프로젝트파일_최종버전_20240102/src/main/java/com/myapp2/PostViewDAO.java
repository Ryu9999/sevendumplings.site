package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class PostViewDAO {

	private static final String SELECT_POST_BY_ID = "SELECT list_num, title, content, id, file_dir, wr_time, edit_time, file FROM list_table WHERE list_num = ?";
	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	private static Connection connection;

	static {
		try (InputStream input = PostViewDAO.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			if (input != null) {
				Properties properties = new Properties();
				properties.load(input);
				JDBC_URL = properties.getProperty("jdbc.url");
				JDBC_USER = properties.getProperty("jdbc.user");
				JDBC_PASSWORD = properties.getProperty("jdbc.password");

				Class.forName("org.mariadb.jdbc.Driver");

				connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			} else {
				throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
			}
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading " + PROPERTIES_FILE, e);
		}
	}

	public Post getPostById(int listNum) {

		Post post = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(SELECT_POST_BY_ID);
			preparedStatement.setInt(1, listNum);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String id = resultSet.getString("id");
				String file_dir = resultSet.getString("file_dir");
				Date wrTime = resultSet.getDate("wr_time");
				Date editTime = resultSet.getDate("edit_time");
				String file = resultSet.getString("file");
				post = new Post(listNum, title, content, id, file_dir, wrTime, editTime, file);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return post;
	}
}
