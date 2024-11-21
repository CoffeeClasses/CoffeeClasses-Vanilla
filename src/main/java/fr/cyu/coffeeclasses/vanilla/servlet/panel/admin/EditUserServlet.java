package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin;

import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import fr.cyu.coffeeclasses.vanilla.service.CourseService;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@WebServlet("/panel/admin/users/edit")
public class EditUserServlet extends HttpServlet {
	UserService userService = UserService.getInstance();
	CourseService courseService = CourseService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> targetUser = userService.findUserFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetUser.isPresent()) {
			// Get available courses for students and teachers
			Set<Course> availableCourses = courseService.getAll();

			// Set attributes for the JSP
			request.setAttribute("target", targetUser.get());
			request.setAttribute("availableCourses", availableCourses);

			request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-edit.jsp").forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> targetOpt = userService.findUserFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetOpt.isPresent()) {
			User target = targetOpt.get();

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String[] courseIds = request.getParameterValues("courses");

			// Check required entries
			if (
				firstName == null || firstName.isEmpty() ||
				lastName == null || lastName.isEmpty() ||
				email == null || email.isEmpty()
			) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données entrées invalides.");
				return;
			}

			// Modify
			target.setFirstName(firstName);
			target.setLastName(lastName);
			try {
				target.setEmail(email);
			} catch (IllegalArgumentException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format mail incorrect.");
				return;
			}
			if (password != null && !password.isEmpty()) {
				target.setPassword(password);
			}

			// Courses
			if (courseIds != null) {
				Set<Course> selectedCourses = new HashSet<>();

				for (String courseId : courseIds) {
					int id;
					try {
						id = Integer.parseInt(courseId);
					} catch (NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formatage sélection des cours invalide.");
						return;
					}
					Optional<Course> course = courseService.find(id);
					if (course.isPresent()) {
						selectedCourses.add(course.get());
					} else {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tentative d'ajout à un cours n'existant pas.");
						return;
					}
				}

				if (target instanceof Student) {
					((Student) target).setCourses(selectedCourses);
				} else if (target instanceof Teacher) {
					((Teacher) target).setCourses(selectedCourses);
				}
			}

			// Sauvegarde
			userService.update(target);

			// Redirect to the admin panel
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}
}
