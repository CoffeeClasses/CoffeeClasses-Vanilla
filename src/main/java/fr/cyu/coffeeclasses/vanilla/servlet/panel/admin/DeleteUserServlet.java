package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.Optional;

@WebServlet("/panel/admin/users/delete")
public class DeleteUserServlet extends HttpServlet {
	UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> target = userService.findUserFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			request.setAttribute("target", target.get());
			request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-remove.jsp").forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> target = userService.findUserFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			userService.unregister(target.get());
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}
}
