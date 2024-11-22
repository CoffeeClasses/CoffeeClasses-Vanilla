package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.user_management;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
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
import java.util.Set;
import java.util.Optional;

@WebServlet("/panel/admin/users")
public class SearchUsersServlet extends HttpServlet {
	// Services
	private final UserService userService = UserService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/user-management/user-search.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> roleString = Optional.ofNullable(request.getParameter("role"));
		Optional<String> search = Optional.ofNullable(request.getParameter("search"));

		// Get role
		Optional<Class<? extends User>> role = roleString.flatMap(r -> switch (r.toLowerCase()) {
			case "student" -> Optional.of(Student.class);
			case "teacher" -> Optional.of(Teacher.class);
			case "administrator" -> Optional.of(Administrator.class);
			default -> Optional.empty();
		});

		// Search
		Set<User> users = userService.searchUsers(role, search);

		request.setAttribute("users", users);
		request.getRequestDispatcher(JSP_PATH).forward(request, response);
	}
}
