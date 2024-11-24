package fr.cyu.coffeeclasses.vanilla.servlet.panel.student;

import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/panel/student/details")
public class StudentSummaryServlet extends HttpServlet {
	// JSP
	private final String JSP_PATH = "/WEB-INF/views/pages/panel/student/student-summary.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getAttribute("user");
		Student student = (Student) user;
		req.setAttribute("target", student);
		req.getRequestDispatcher(JSP_PATH).forward(req, resp);
	}
}
