package fr.cyu.coffeeclasses.vanilla.servlet.pre_auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Attempt to authenticate the user
		Optional<Integer> userId = UserDAO.getInstance().authenticate(username, password);

		if (userId.isPresent()) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId.get());
			response.sendRedirect("/panel");
		} else {
			request.setAttribute("errorMessage", "Identifiants invalides.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/login.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/login.jsp");
		dispatcher.forward(request, response);
	}
}
