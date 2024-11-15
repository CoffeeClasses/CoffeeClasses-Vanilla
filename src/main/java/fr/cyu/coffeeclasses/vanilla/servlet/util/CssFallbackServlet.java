package fr.cyu.coffeeclasses.vanilla.servlet.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

@WebServlet("/css/pages/*")
public class CssFallbackServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the requested CSS file path
		String cssPath = request.getPathInfo();
		File cssFile = new File(getServletContext().getRealPath("/css/pages/" + cssPath));

		if (cssFile.exists() && cssFile.isFile()) {
			// If the file exists, serve it normally
			response.setContentType("text/css");
			try (BufferedReader reader = Files.newBufferedReader(cssFile.toPath());
				 PrintWriter writer = response.getWriter()) {

				String line;
				while ((line = reader.readLine()) != null) {
					writer.println(line);
				}
			}
		} else {
			// If the file does not exist, serve an empty CSS response
			response.setContentType("text/css");
			response.getWriter().write("/* Empty CSS fallback */");
		}
	}
}
