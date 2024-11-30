package fr.cyu.coffeeclasses.vanilla.servlet.panel.student;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.PdfGeneratorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/panel/student/details")
public class StudentSummaryServlet extends HttpServlet {
    // Service
    private final static PdfGeneratorService pdfGeneratorService = PdfGeneratorService.getInstance();
    // JSP
    private final static String JSP_PATH = "/WEB-INF/views/pages/panel/student/student-summary.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        Student student = (Student) user;
        req.setAttribute("target", student);
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        Student student = (Student) user;

        try {
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=notes-etudiant-" + user.getId() + ".pdf");
            pdfGeneratorService.generateStudentSummary(student, resp.getOutputStream());
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du PDF", e);
        }
    }
}
