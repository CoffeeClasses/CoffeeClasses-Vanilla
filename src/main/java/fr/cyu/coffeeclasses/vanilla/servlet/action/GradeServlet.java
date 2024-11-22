package fr.cyu.coffeeclasses.vanilla.servlet.action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import fr.cyu.coffeeclasses.vanilla.database.dao.AssessmentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.GradeDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataUpdateException;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;


@WebServlet("/grade")
public class GradeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String strId = request.getParameter("assessmentId");
			int assessmentId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());
			Assessment assessment = AssessmentDAO.getInstance().findById(assessmentId).orElseThrow();
			if(assessment.getCourse().getTeacher().getId().orElseThrow()!=session.getAttribute("userId")) {
				response.sendRedirect("panel");
			}
			request.setAttribute("assessment", assessment);
			
			ArrayList<Student> students = (ArrayList<Student>) StudentDAO.getInstance().getAll();
			HashSet<Student> concerned = new HashSet<>();
			for(Student s: students) {
				for(Enrollment e: s.getEnrollments()) {
					if(e.getCourse().getId().orElseThrow() == assessment.getCourse().getId().orElseThrow()) {
						concerned.add(s);
						break;
					}
				}
			}
			students = new ArrayList<>(concerned);
			students.sort(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName));
			request.setAttribute("students", students);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/assessmentGrading.jsp");
			dispatcher.forward(request, response);
		}catch(NullPointerException | ClassCastException | IllegalArgumentException | NoSuchElementException e) {
			response.sendRedirect("panel");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("assessmentId");
		int assessmentId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());
		Assessment assessment = AssessmentDAO.getInstance().findById(assessmentId).orElseThrow();
		
		ArrayList<Student> students = (ArrayList<Student>) StudentDAO.getInstance().getAll();
		HashSet<Student> concerned = new HashSet<>();
		for(Student s: students) {
			for(Enrollment e: s.getEnrollments()) {
				if(e.getCourse().getId().orElseThrow() == assessment.getCourse().getId().orElseThrow()) {
					concerned.add(s);
					break;
				}
			}
		}
		GradeDAO gradeDAO = GradeDAO.getInstance();
		double newValue;
		for(Student s: concerned) {
			newValue = Double.parseDouble(request.getParameter(s.getId().toString()));
			Optional<Grade> existingGrade = gradeDAO.findByStudentAndAssessment(s, assessment);
	        if (existingGrade.isPresent()) {
	            Grade grade = existingGrade.get();
	            grade.setValue(newValue);
	            gradeDAO.update(grade);
	        } else {
	            Grade newGrade = new Grade(assessment, s, newValue);
	            gradeDAO.save(newGrade);
	        }
		}
		request.setAttribute("successMessage", "Les notes ont bien été eregistrées.");
		new AssignServlet().doGet(request,response);
	}

}
