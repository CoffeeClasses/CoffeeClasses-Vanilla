package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin;

import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/panel/admin/users/add")
public class AddUserServlet extends HttpServlet {
	private final UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-add.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String birthDateString = request.getParameter("birthDate");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

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

		// Check date
		LocalDate birthDate;
		try {
			birthDate = LocalDate.parse(birthDateString);
		} catch (DateTimeParseException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format de date invalide. Utilisez AAAA-MM-JJ.");
			return;
		}

		User newUser;
		switch (role) {
			case "student":
				newUser = Student.create(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			case "teacher":
				newUser = Teacher.create(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			case "administrator":
				newUser = Administrator.create(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rôle invalide.");
				return;
		}

		userService.save(newUser);
		response.sendRedirect(request.getContextPath() + "/panel/admin/users");
	}
}
