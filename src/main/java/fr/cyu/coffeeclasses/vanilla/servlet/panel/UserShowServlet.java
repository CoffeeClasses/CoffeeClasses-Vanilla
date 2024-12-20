package fr.cyu.coffeeclasses.vanilla.servlet.panel;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/panel/users/show")
public class UserShowServlet extends HttpServlet {
	// Services
	private final UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> targetOpt = userService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetOpt.isPresent()) {
			request.setAttribute("target", targetOpt.get());
			request.getRequestDispatcher("/WEB-INF/views/pages/panel/user-show.jsp").forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}
}
