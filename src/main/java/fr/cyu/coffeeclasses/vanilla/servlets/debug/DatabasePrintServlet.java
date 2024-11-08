package fr.cyu.coffeeclasses.vanilla.servlets.debug;

import fr.cyu.coffeeclasses.vanilla.database.daos.*;
import fr.cyu.coffeeclasses.vanilla.entities.elements.*;
import fr.cyu.coffeeclasses.vanilla.entities.users.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/display_data")
public class DatabasePrintServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve data from the database
        List<Student> students = StudentDAO.getInstance().getAll();
        List<Teacher> teachers = TeacherDAO.getInstance().getAll();
        List<Course> courses = CourseDAO.getInstance().getAll();
        List<Assessment> assessments = AssessmentDAO.getInstance().getAll();
        List<Enrollment> enrollments = EnrollmentDAO.getInstance().getAll();
        List<Grade> grades = GradeDAO.getInstance().getAll();

        // Set the data as request attributes
        request.setAttribute("students", students);
        request.setAttribute("teachers", teachers);
        request.setAttribute("courses", courses);
        request.setAttribute("assessments", assessments);
        request.setAttribute("enrollments", enrollments);
        request.setAttribute("grades", grades);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/database_print.jsp");
        dispatcher.forward(request, response);
    }
}
