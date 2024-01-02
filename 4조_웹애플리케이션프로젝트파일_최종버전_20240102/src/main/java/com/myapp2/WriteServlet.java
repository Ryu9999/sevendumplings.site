package com.myapp2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/WriteServlet")
@MultipartConfig
public class WriteServlet extends HttpServlet {
	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;

	static {
		try (InputStream input = WriteServlet.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
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

		request.setCharacterEncoding("UTF-8");

		String directory = "D:/uploaded";
		int sizeLimit = 100 * 1024 * 1024;
		File targetDir = new File(directory);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		try {
			List<Part> parts = (List<Part>) request.getParts();

			for (Part part : parts) {
				if (part.getName().equals("file")) {
					String fileName = extractFileName(part);
					String filePath = directory + File.separator + fileName;
					try (InputStream fileContent = part.getInputStream()) {
						Files.copy(fileContent, Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}

					String title = request.getParameter("title");
					String content = request.getParameter("content");

					try {

						Class.forName("org.mariadb.jdbc.Driver");
						try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

							String sql = "INSERT INTO list_table (title, content, id, file, wr_time, edit_time, del_flag, board_op) VALUES (?, ?, ?, ?, NOW(), NOW(), 'N', ?)";
							try (PreparedStatement statement = connection.prepareStatement(sql)) {

								statement.setString(1, title);
								statement.setString(2, content);
								String username = (String) request.getSession().getAttribute("username");
								statement.setString(3, username);
								statement.setString(4, fileName);
								statement.setString(5, "Q&A");

								statement.executeUpdate();
							}

							response.sendRedirect("board?type=vulnerability");
						}
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();

						request.setAttribute("error", "글 작성 중 오류가 발생했습니다.");
						request.getRequestDispatcher("error.jsp").forward(request, response);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String extractFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		String[] items = contentDisposition.split(";");
		for (String item : items) {
			if (item.trim().startsWith("filename")) {
				return item.substring(item.indexOf("=") + 2, item.length() - 1);
			}
		}
		return "";
	}
}
