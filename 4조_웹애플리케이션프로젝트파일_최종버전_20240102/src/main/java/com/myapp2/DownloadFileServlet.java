package com.myapp2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadfile")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileName = request.getParameter("file");
		String directory = "D:" + File.separator + "uploaded";

		File file = new File(directory + File.separator + fileName);

		String mimeType = getServletContext().getMimeType(file.toString());
		if (mimeType == null) {
			response.setContentType("application/octet-stream");
		}

		String downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + "\";");

		FileInputStream fileInputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = response.getOutputStream();

		byte b[] = new byte[1024];
		int data = 0;

		while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
			servletOutputStream.write(b, 0, data);
		}
		servletOutputStream.flush();
		servletOutputStream.close();
		fileInputStream.close();
	}
}
