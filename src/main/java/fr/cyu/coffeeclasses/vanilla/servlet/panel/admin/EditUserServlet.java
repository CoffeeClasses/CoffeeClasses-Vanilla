package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin;

import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.Optional;

@WebServlet("/panel/admin/users/edit")
public class EditUserServlet extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(EditUserServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> userIdString = Optional.ofNullable(request.getParameter("id"));
		if (userIdString.isEmpty()) {
			logger.warn("ID missing.");
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		int userId;
		try {
			userId = Integer.parseInt(userIdString.get());
		} catch (NumberFormatException e) {
			logger.warn("ID format exception.");
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}

		Optional<User> userOptional = UserDAO.getInstance().findById(userId);
		if (userOptional.isEmpty()) {
			logger.warn("User not found.");
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
			return;
		}
		User user = userOptional.get();

		// Get available courses for students and teachers
		Set<Course> availableCourses = CourseDAO.getInstance().getAll();

		// Set attributes for the JSP
		request.setAttribute("target", user);
		request.setAttribute("availableCourses", availableCourses);

		request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-edit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Placeholder

		response.sendRedirect("/panel/admin/users");
	}
}
