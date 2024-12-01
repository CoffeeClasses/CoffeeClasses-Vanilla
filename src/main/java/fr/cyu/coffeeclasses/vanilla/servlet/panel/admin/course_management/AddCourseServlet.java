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
import java.util.Optional;

@WebServlet("/panel/admin/courses/add")
public class AddCourseServlet extends HttpServlet {
	// Services
	CourseService courseService = CourseService.getInstance();
	TeacherService teacherService = TeacherService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/admin/course-management/course-add.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("teachers", teacherService.getAll());
		req.getRequestDispatcher(JSP_PATH).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String teacherIdStr = req.getParameter("teacherId");

		try {
			// Validate teacher ID
			int teacherId = Integer.parseInt(teacherIdStr);
			Optional<Teacher> teacher = teacherService.find(teacherId);
			if (teacher.isEmpty()) {
				req.setAttribute("errorMessage", "Le professeur sélectionné n'existe pas.");
				req.getRequestDispatcher(JSP_PATH).forward(req, resp);
				return;
			}

			// Create and save the new course
			Course course = new Course(name, teacher.get());
			courseService.save(course);

			// Redirect to course management page
			resp.sendRedirect(req.getContextPath() + "/panel/admin/courses");
		} catch (NumberFormatException e) {
			req.setAttribute("errorMessage", "ID Professeur invalide.");
			req.getRequestDispatcher(JSP_PATH).forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("errorMessage", "Une erreur a empêché l'ajout du cours.");
			req.getRequestDispatcher(JSP_PATH).forward(req, resp);
		}
	}
}
