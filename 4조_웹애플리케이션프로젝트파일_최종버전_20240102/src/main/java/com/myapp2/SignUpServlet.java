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

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	static {
		try (InputStream input = SignUpServlet.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
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
		String nickname = request.getParameter("nickname");
		String plainPassword = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String salt = PasswordHasher.generateSalt();
		String hashedPassword = PasswordHasher.hashPassword(plainPassword, salt);
		

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
				String sql = "INSERT INTO account_table (nickname, user_password, salt, email, phone_number, role) VALUES (?, ?, ?, ?, ?, 'user')";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, nickname);
					statement.setString(2, hashedPassword);
					statement.setString(3, salt);
					statement.setString(4, email);
					statement.setString(5, phone);
					statement.executeUpdate();
					response.sendRedirect("mainboard.jsp");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "회원가입 중 오류가 발생했습니다.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
