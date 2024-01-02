package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String plainPassword = request.getParameter("password");

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
				String sql = "SELECT * FROM account_table WHERE nickname = ? LIMIT 1";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, username);

					ResultSet resultSet = statement.executeQuery();

					if (resultSet.next()) {
						String hashedPassword = resultSet.getString("user_password");
						String salt = resultSet.getString("salt");

						if (verifyPassword(plainPassword, hashedPassword, salt)) {
							authenticateUser(request, username);
							response.sendRedirect(request.getContextPath() + "/mainboard.jsp");
						} else {
							request.setAttribute("error", "Invalid username or password");
							request.getRequestDispatcher("login.jsp").forward(request, response);
						}
					} else {
						request.setAttribute("error", "Invalid username or password");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private boolean verifyPassword(String plainPassword, String hashedPassword, String salt) {
		String hashedInputPassword = PasswordHasher.hashPassword(plainPassword, salt);
		return hashedInputPassword.equals(hashedPassword);
	}

	private void authenticateUser(HttpServletRequest request, String username) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		if ("admin".equals(username)) {
			session.setAttribute("role", "admin");
		} else {
			session.setAttribute("role", "user");
		}
	}
}
