package com.myapp2;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String boardType = request.getParameter("type");

		BoardDAO boardDAO = new BoardDAO();

		List<Post> posts = boardDAO.getPostsByType(boardType);

		request.setAttribute("posts", posts);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainboard.jsp");
		dispatcher.forward(request, response);
	}
}
