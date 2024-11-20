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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> userIdString = Optional.ofNullable(request.getParameter("id"));
		if (userIdString.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		int userId;
		try {
			userId = Integer.parseInt(userIdString.get());
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		Optional<User> userOptional = UserDAO.getInstance().findById(userId);
		if (userOptional.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}
		User target = userOptional.get();

		// Redirect to user remove page
		request.setAttribute("target", target);
		request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-remove.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> userIdString = Optional.ofNullable(request.getParameter("id"));
		if (userIdString.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		int userId;
		try {
			userId = Integer.parseInt(userIdString.get());
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		Optional<User> userOptional = UserDAO.getInstance().findById(userId);
		if (userOptional.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}
		User target = userOptional.get();

		// Delete
		UserService.getInstance().unregister(target);

		// Redirect to user panel
		response.sendRedirect(request.getContextPath() + "/panel/admin/users");
	}
}
