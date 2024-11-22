package fr.cyu.coffeeclasses.vanilla.servlet.panel.admin.course_management;

import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.service.CourseService;
import fr.cyu.coffeeclasses.vanilla.service.TeacherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/panel/admin/courses")
public class ListCoursesServlet extends HttpServlet {
	// Services
	CourseService courseService = CourseService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/course-management/course-list.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<String> search = Optional.ofNullable(request.getParameter("search"));

		// Fetch courses
		Set<Course> courses = courseService.searchCourses(search);

		// Sort courses alphabetically by name
		Set<Course> sortedCourses = courses.stream()
				.sorted((course1, course2) -> course1.getName().compareToIgnoreCase(course2.getName()))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		// Set the sorted courses in the request
		request.setAttribute("courses", sortedCourses);
		request.getRequestDispatcher(JSP_PATH).forward(request, response);
	}
}
