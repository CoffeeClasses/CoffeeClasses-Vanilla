package fr.cyu.coffeeclasses.vanilla.servlet.pre_auth;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import fr.cyu.coffeeclasses.vanilla.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");

		// Attempt to authenticate the user
		Optional<User> user = UserService.getInstance().authenticate(mail, password);

		if (user.isPresent()) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.get().getId());

			response.sendRedirect(request.getContextPath() + "/panel/home");
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
