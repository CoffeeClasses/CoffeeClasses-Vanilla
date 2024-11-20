package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.Optional;

@WebServlet(name = "SearchUsersServlet", urlPatterns = "/panel/admin/users")
public class SearchUsersServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> roleString = Optional.ofNullable(request.getParameter("role"));
		Optional<String> search = Optional.ofNullable(request.getParameter("search"));

		// Get role
		Optional<Class<? extends User>> role = Optional.empty();
		if (roleString.isPresent()) {
			role = switch (roleString.get().toLowerCase()) {
				case "student" -> Optional.of(Student.class);
				case "teacher" -> Optional.of(Teacher.class);
				case "administrator" -> Optional.of(Administrator.class);
				default -> Optional.empty();
			};
		}

		// Search
		Set<User> users = UserDAO.getInstance().searchUsers(role, search);

		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/views/pages/panel/admin/user-search.jsp").forward(request, response);
	}
}
