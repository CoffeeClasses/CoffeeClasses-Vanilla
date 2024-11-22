package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.user_management;

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
	// Services
	private final UserService userService = UserService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/user-management/user-add.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(JSP_PATH).forward(request, response);
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
			request.setAttribute("errorMessage", "Données entrées invalides.");
			request.getRequestDispatcher(JSP_PATH).forward(request, response);
			return;
		}

		// Check date
		LocalDate birthDate;
		try {
			birthDate = LocalDate.parse(birthDateString);
		} catch (DateTimeParseException e) {
			request.setAttribute("errorMessage", "Format de date invalide. Utilisez AAAA-MM-JJ.");
			request.getRequestDispatcher(JSP_PATH).forward(request, response);
			return;
		}

		User newUser;
		switch (role) {
			case "student":
				newUser = new Student(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			case "teacher":
				newUser = new Teacher(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			case "administrator":
				newUser = new Administrator(
						firstName,
						lastName,
						email,
						password,
						birthDate
				);
				break;
			default:
				request.setAttribute("errorMessage", "Rôle invalide.");
				request.getRequestDispatcher(JSP_PATH).forward(request, response);
				return;
		}

		userService.save(newUser);
		response.sendRedirect(request.getContextPath() + "/panel/admin/users");
	}
}
