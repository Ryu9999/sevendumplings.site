package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	static {
		try (InputStream input = DeleteCommentServlet.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String listNumParam = request.getParameter("listNum");

		if (listNumParam != null) {
			int listNum = Integer.parseInt(listNumParam);

			try {
				Class.forName("org.mariadb.jdbc.Driver");
				try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
					String sql = "DELETE FROM list_table WHERE list_num = ?";
					try (PreparedStatement statement = connection.prepareStatement(sql)) {
						statement.setInt(1, listNum);
						int rowsAffected = statement.executeUpdate();

						if (rowsAffected > 0) {
							response.sendRedirect(request.getContextPath() + "/mainboard.jsp");
						} else {
							response.sendRedirect(request.getContextPath() + "/mainboard.jsp");
						}
					}
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				response.sendRedirect("error.jsp");
			}
		} else {
			response.sendRedirect("error.jsp");
		}
	}
}
