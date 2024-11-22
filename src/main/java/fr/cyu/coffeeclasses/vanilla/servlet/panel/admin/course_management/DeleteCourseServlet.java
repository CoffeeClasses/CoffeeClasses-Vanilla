package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.course_management;

import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.CourseService;
import fr.cyu.coffeeclasses.vanilla.service.TeacherService;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@WebServlet("/panel/admin/courses/delete")
public class DeleteCourseServlet extends HttpServlet {
	// Services
	CourseService courseService = CourseService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/course-management/course-delete.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Course> target = courseService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			request.setAttribute("target", target.get());
			request.getRequestDispatcher(JSP_PATH).forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cours spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Course> target = courseService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (target.isPresent()) {
			courseService.delete(target.get());
			response.sendRedirect(request.getContextPath() + "/panel/admin/courses");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cours spécifié absent ou invalide.");
		}
	}
}
