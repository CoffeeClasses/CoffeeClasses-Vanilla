package fr.cyu.coffeeclasses.vanilla.servlet.panel;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataNonsenseException;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		String userType = (String) session.getAttribute("userType");

		if (userId == null || userType == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Fetch user details
		User user = UserDAO.getInstance().findById(userId).orElseThrow(() -> new DataNonsenseException("User not found while requesting for info."));
		if (user.getClass() == Teacher.class) {
			request.setAttribute("courses", ((Teacher) user).getCourses());
		} else if (user.getClass() == Student.class) {
			request.setAttribute("enrollments", ((Student) user).getEnrollments());
		}
		request.setAttribute("user", user);
		request.setAttribute("userType", userType);

		// Forward to the profile page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/profile.jsp");
		dispatcher.forward(request, response);
	}
}
