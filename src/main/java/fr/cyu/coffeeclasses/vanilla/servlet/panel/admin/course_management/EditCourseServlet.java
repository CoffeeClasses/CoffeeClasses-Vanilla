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
import java.util.Set;

@WebServlet("/panel/admin/courses/edit")
public class EditCourseServlet extends HttpServlet {
	// Services
	private final CourseService courseService = CourseService.getInstance();
	private final TeacherService teacherService = TeacherService.getInstance();
	// JSP
	private static final String JSP_PATH = "/WEB-INF/views/pages/panel/admin/course-management/course-edit.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Course> targetCourse = courseService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetCourse.isPresent()) {
			Set<Teacher> teachers = teacherService.getAll();

			request.setAttribute("targetCourse", targetCourse.get());
			request.setAttribute("teachers", teachers);

			request.getRequestDispatcher(JSP_PATH).forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cours spécifié absent ou invalide.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Course> targetOpt = courseService.findFromIDParameter(Optional.ofNullable(request.getParameter("id")));
		if (targetOpt.isPresent()) {
			Course target = targetOpt.get();

			String name = request.getParameter("name");
			String teacherId = request.getParameter("teacher");

			if (name == null || name.isEmpty() || teacherId == null || teacherId.isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données entrées invalides.");
				return;
			}

			// Update course name
			target.setName(name);

			// Update teacher
			try {
				int teacherIdParsed = Integer.parseInt(teacherId);
				Optional<Teacher> teacher = teacherService.find(teacherIdParsed);
				if (teacher.isPresent()) {
					target.setTeacher(teacher.get());
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enseignant spécifié introuvable.");
					return;
				}
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant de l'enseignant invalide.");
				return;
			}

			// Save changes
			courseService.update(target);

			// Redirect to the courses admin panel
			response.sendRedirect(request.getContextPath() + "/panel/admin/courses");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cours spécifié absent ou invalide.");
		}
	}
}
