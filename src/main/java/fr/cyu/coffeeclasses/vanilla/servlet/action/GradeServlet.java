package fr.cyu.coffeeclasses.vanilla.servlet.action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import fr.cyu.coffeeclasses.vanilla.database.dao.AssessmentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;

/**
 * Servlet implementation class GradeAssessment
 */
@WebServlet("/grade")
public class GradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		
	}

}
