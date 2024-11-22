package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.user_management;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/panel/admin/users/delete")
public class DeleteUserServlet extends HttpServlet {
	// Services
	private final UserService userService = UserService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/user-management/user-delete.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> target = userService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			request.setAttribute("target", target.get());
			request.getRequestDispatcher(JSP_PATH).forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> target = userService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			userService.unregister(target.get());
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}
}
