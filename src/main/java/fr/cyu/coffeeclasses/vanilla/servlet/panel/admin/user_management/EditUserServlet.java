package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.user_management;


import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.mail.MailSender;
import fr.cyu.coffeeclasses.vanilla.service.CourseService;
import fr.cyu.coffeeclasses.vanilla.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@WebServlet("/panel/admin/users/edit")
public class EditUserServlet extends HttpServlet {
	// Services
	private final UserService userService = UserService.getInstance();
	private final CourseService courseService = CourseService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/user-management/user-edit.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> targetUser = userService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetUser.isPresent()) {
			// Get available courses for students and teachers
			Set<Course> availableCourses = courseService.getAll();

			// Set attributes for the JSP
			request.setAttribute("target", targetUser.get());
			request.setAttribute("availableCourses", availableCourses);

			request.getRequestDispatcher(JSP_PATH).forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> targetOpt = userService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetOpt.isPresent()) {
			User target = targetOpt.get();

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String birthDateString = request.getParameter("birthDate");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String[] courseIds = request.getParameterValues("courses");

			// Check required entries
			if (
				firstName == null || firstName.isEmpty() ||
				lastName == null || lastName.isEmpty() ||
				birthDateString == null || birthDateString.isEmpty() ||
				email == null || email.isEmpty()
			) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données entrées invalides.");
				return;
			}

			// Modify
			target.setFirstName(firstName);
			target.setLastName(lastName);
			try {
				target.setBirthDate(LocalDate.parse(birthDateString));
			} catch (DateTimeParseException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format de date invalide. Utilisez AAAA-MM-JJ.");
				return;
			}
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
			MailSender.getInstance().sendMail(target, "Données mises à jour", "Bonjour,\nVos informations ont bien été mises à jours par la scolarité.");

			// Redirect to the admin panel
			response.sendRedirect(request.getContextPath() + "/panel/admin/users");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur spécifié absent ou invalide.");
		}
	}
}
