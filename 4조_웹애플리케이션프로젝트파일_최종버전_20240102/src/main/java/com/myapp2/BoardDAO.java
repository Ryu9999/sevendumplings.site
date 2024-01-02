package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BoardDAO {
	private static final String SELECT_TYPE_POSTS = "SELECT list_num, title, content, id, file_dir, wr_time, edit_time FROM %s";

	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	private static String JDBC_TABLE1;
	private static String JDBC_TABLE2;
	private static String JDBC_TABLE3;

	static {
		try (InputStream input = BoardDAO.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			if (input != null) {
				Properties properties = new Properties();
				properties.load(input);
				JDBC_URL = properties.getProperty("jdbc.url");
				JDBC_USER = properties.getProperty("jdbc.user");
				JDBC_PASSWORD = properties.getProperty("jdbc.password");

				JDBC_TABLE1 = properties.getProperty("jdbc.table1");
				JDBC_TABLE2 = properties.getProperty("jdbc.table2");
				JDBC_TABLE3 = properties.getProperty("jdbc.table3");

				Class.forName("org.mariadb.jdbc.Driver");
			} else {
				throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading " + PROPERTIES_FILE, e);
		}
	}

	public List<Post> getPostsByType(String boardType) {

		List<Post> postList = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Establish the database connection
			connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			switch (boardType) {
			case "vulnerability":
				boardType = JDBC_TABLE1;
				break;
			case "secure-code":
				boardType = JDBC_TABLE2;
				break;
			case "qna":
				boardType = JDBC_TABLE3;
				break;
			default:
				boardType = "boardType";
				break;
			}

			String dynamicQuery = String.format(SELECT_TYPE_POSTS, boardType);
			preparedStatement = connection.prepareStatement(dynamicQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int listNum = resultSet.getInt("list_num");
				String title = resultSet.getString("title");
				String id = resultSet.getString("id");
				Date wrTime = resultSet.getDate("wr_time");

				Post post = new Post(listNum, title, id, wrTime);
				postList.add(post);
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
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return postList;
	}
}