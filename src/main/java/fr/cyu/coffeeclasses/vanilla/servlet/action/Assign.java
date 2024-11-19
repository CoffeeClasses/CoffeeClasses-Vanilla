package fr.cyu.coffeeclasses.vanilla.servlet.action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import fr.cyu.coffeeclasses.vanilla.database.dao.AssessmentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;

/**
 * Servlet implementation class Assign
 */
@WebServlet("/assessment")
public class Assign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			Integer id = (Integer) session.getAttribute("userId");
			Teacher loggedTeacher = TeacherDAO.getInstance().findById(id).orElseThrow();
			request.setAttribute("courses", loggedTeacher.getCourses());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/assessment.jsp");
			dispatcher.forward(request, response);
		}catch(NullPointerException | ClassCastException | IllegalArgumentException e) {
			response.sendRedirect("/WEB-INF/views/pages/panel.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String strId = request.getParameter("selectedCourse");
			int courseId = Integer.parseInt(strId.substring(9, strId.length() - 1).trim());
			int maxGrade = Integer.parseInt(request.getParameter("maxGrade"));
			String name = request.getParameter("name");
			
			Course course = CourseDAO.getInstance().findById(courseId).orElseThrow();
			Assessment assessment = new Assessment(name,LocalDateTime.now(),maxGrade,course);
			AssessmentDAO.getInstance().save(assessment);
			
			request.setAttribute("successMessage", "L'évaluation a bien été enregistrée.");
			this.doGet(request, response);
		}catch(NullPointerException | StringIndexOutOfBoundsException |IllegalArgumentException e) {
			request.setAttribute("errorMessage", "Une erreur est survenue.");
			this.doGet(request, response);
		}
	}

}
