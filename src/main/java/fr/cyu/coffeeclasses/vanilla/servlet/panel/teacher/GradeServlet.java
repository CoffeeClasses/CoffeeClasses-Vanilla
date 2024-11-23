package fr.cyu.coffeeclasses.vanilla.servlet.panel.teacher;

import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.service.AssessmentService;
import fr.cyu.coffeeclasses.vanilla.service.GradeService;
import fr.cyu.coffeeclasses.vanilla.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;

@WebServlet("/panel/teacher/grades")
public class GradeServlet extends HttpServlet {
	// Services
	private final static AssessmentService assessmentService = AssessmentService.getInstance();
	private final static StudentService studentService = StudentService.getInstance();
	private final static GradeService gradeService = GradeService.getInstance();
	// JSP
	private final static String JSP_PATH = "/WEB-INF/views/pages/assessment-grading.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Find Assessment
			String strId = request.getParameter("assessmentId");
			int assessmentId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());
			Assessment assessment = assessmentService.find(assessmentId).orElseThrow();

			// Find Teacher
			Teacher teacher = (Teacher) request.getAttribute("user");
			if (assessment.getTeacher().getId() != teacher.getId()) {
				response.sendRedirect(request.getContextPath() + "/panel/home");
				return;
			}
			request.setAttribute("assessment", assessment);
			Map<Student, Grade> studentGrades = new HashMap<>();
			for (Student student : assessment.getStudents()) {
				studentGrades.put(student, assessment.getGradeForStudent(student).orElse(null));
			}
			request.setAttribute("studentGrades", studentGrades);
			request.getRequestDispatcher(JSP_PATH).forward(request, response);
		}catch(Exception e) {
			response.sendRedirect(request.getContextPath() + "/panel/home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("assessmentId");
		int assessmentId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());

		Assessment assessment = assessmentService.find(assessmentId).orElseThrow();
		Set<Student> concerned = assessment.getStudents();

		double newValue;
		for(Student s: concerned) {
			newValue = Double.parseDouble(request.getParameter(String.valueOf(s.getId())));
			Optional<Grade> existingGrade = gradeService.findByStudentAndAssessment(s, assessment);

	        if (existingGrade.isPresent()) {
	            Grade grade = existingGrade.get();
	            grade.setValue(newValue);
				gradeService.update(grade);
	        } else {
	            Grade newGrade = new Grade(assessment, assessment.getCourse().getEnrollmentForStudent(s).orElseThrow(), newValue);
				gradeService.save(newGrade);
	        }
		}
		request.setAttribute("successMessage", "Les notes ont bien été eregistrées.");
		new AssignServlet().doGet(request,response);
	}

}
