package com.myapp2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewPostServlet")
public class ViewPostServlet extends HttpServlet {

	private static final String PROPERTIES_FILE = "db.properties";
	private static String JDBC_URL;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;
	private CommentDAO commentDAO;

	@Override
	public void init() throws ServletException {

		try (InputStream input = ViewPostServlet.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
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

		commentDAO = new CommentDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String listNumParam = request.getParameter("listNum");
		response.setCharacterEncoding("UTF-8");
		if (listNumParam != null) {

			int listNum = Integer.parseInt(listNumParam);

			PostViewDAO postViewDAO = new PostViewDAO();
			Post post = postViewDAO.getPostById(listNum);

			if (post != null) {

				request.setAttribute("post", post);

				boolean isLoggedIn = request.getSession().getAttribute("username") != null;
				boolean isAdmin = "admin".equals(request.getSession().getAttribute("username"));

				request.setAttribute("isLoggedIn", isLoggedIn);
				request.setAttribute("isAdmin", isAdmin);

				List<Comment> comments = getCommentsForPost(listNum);
				request.setAttribute("comments", comments);

				String currentURL = request.getRequestURI() + "?" + request.getQueryString();
				request.getSession().setAttribute("previousPage", currentURL);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/viewpost.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/error.jsp");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String username = (String) request.getSession().getAttribute("username");
		boolean isLoggedIn = username != null;
		if (isLoggedIn) {

			String postIdParam = request.getParameter("postId");
			String commentContent = request.getParameter("commentContent");
			String nickname = (String) request.getSession().getAttribute("username");

			if (postIdParam != null && commentContent != null) {
				int postId = Integer.parseInt(postIdParam);

				if (addComment(nickname, postId, commentContent)) {
					request.setAttribute("commentSuccess", true);

					String previousPage = (String) request.getSession().getAttribute("previousPage");
					if (previousPage != null && !previousPage.isEmpty()) {
						response.sendRedirect(previousPage);
						return;
					}
				} else {
					request.setAttribute("commentError", "Failed to add the comment");
				}
			} else {
				request.setAttribute("commentError", "Invalid parameters");
			}
		} else {
			request.setAttribute("commentError", "You must be logged in to comment");
		}
	}

	private boolean addComment(String nickname, int postId, String commentContent) {
		return commentDAO.addComment(nickname, postId, commentContent);
	}

	private List<Comment> getCommentsForPost(int postId) {
		List<Comment> comments = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
			String sql = "SELECT * FROM comment_table WHERE board_op = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, postId);

				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						Comment comment = new Comment();
						comment.setId(resultSet.getInt("id"));
						comment.setUsername(resultSet.getString("nickname"));
						comment.setCommentContent(resultSet.getString("comment"));
						comment.setCommentTime(resultSet.getTimestamp("wr_time"));
						comments.add(comment);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException during retrieving comments: " + e.getMessage());
			e.printStackTrace();
		}

		return comments;
	}
}
