package fr.cyu.coffeeclasses.vanilla.servlet.panel.teacher;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.AssessmentService;
import fr.cyu.coffeeclasses.vanilla.service.CourseService;
import fr.cyu.coffeeclasses.vanilla.service.TeacherService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import fr.cyu.coffeeclasses.vanilla.database.dao.AssessmentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;


@WebServlet("/panel/teacher/assessments")
public class AssignServlet extends HttpServlet {
	// Services
	private final static CourseService courseService = CourseService.getInstance();
	private final static AssessmentService assessmentService = AssessmentService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/panel/teacher/assessment.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getAttribute("user");
		request.setAttribute("teacher", teacher);
		request.getRequestDispatcher(JSP_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String strId = request.getParameter("selectedCourse");
			int courseId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());
			int maxGrade = Integer.parseInt(request.getParameter("maxGrade"));
			String name = request.getParameter("name");
			String dateString = request.getParameter("date");

			// Check date
			LocalDate date;
			try {
				date = LocalDate.parse(dateString);
			} catch (DateTimeParseException e) {
				request.setAttribute("errorMessage", "Format de date invalide. Utilisez AAAA-MM-JJ.");
				this.doGet(request, response);
				return;
			}
			
			Course course = courseService.find(courseId).orElseThrow();
			Assessment assessment = new Assessment(name,date,maxGrade,course);
			assessmentService.save(assessment);
			
			request.setAttribute("successMessage", "L'évaluation a bien été enregistrée.");
			this.doGet(request, response);
		}catch(Exception e) {
			request.setAttribute("errorMessage", "Une erreur est survenue.");
			this.doGet(request, response);
		}
	}

}
