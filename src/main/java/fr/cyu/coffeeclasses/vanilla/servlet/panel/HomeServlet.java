package fr.cyu.coffeeclasses.vanilla.servlet.panel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/panel")
public class HomeServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is connected
		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/home.jsp");
			dispatcher.forward(request, response);
		} else response.sendRedirect(request.getContextPath() + "/login");
	}
}
