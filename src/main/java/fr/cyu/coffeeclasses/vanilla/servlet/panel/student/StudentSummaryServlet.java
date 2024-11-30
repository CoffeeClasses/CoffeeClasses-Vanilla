package fr.cyu.coffeeclasses.vanilla.servlet.panel.student;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/panel/student/details")
public class StudentSummaryServlet extends HttpServlet {
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

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=notes-etudiant.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            // Polices personnalisées
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font subTitleFont = new Font(baseFont, 14, Font.BOLD);
            Font textFont = new Font(baseFont, 12, Font.NORMAL);

            // Titre principal
            Paragraph title = new Paragraph("Notes de l'étudiant", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Espace

            // Informations de l'étudiant
            Paragraph studentInfo = new Paragraph("Nom : " + student.getFirstName() + " " + student.getLastName(), textFont);
            document.add(studentInfo);

            document.add(new Paragraph(" ")); // Espace

            if (student.getEnrollments() != null && !student.getEnrollments().isEmpty()) {
                for (var enrollment : student.getEnrollments()) {
                    // Nom du cours
                    Paragraph courseTitle = new Paragraph("Cours : " + enrollment.getCourse().getName(), subTitleFont);
                    document.add(courseTitle);

                    // Tableau des notes
                    PdfPTable table = new PdfPTable(3); // Colonnes : Évaluation, Note, Maximum
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    // En-têtes du tableau
                    Font headerFont = new Font(baseFont, 12, Font.BOLD);
                    PdfPCell cell1 = new PdfPCell(new Phrase("Évaluation", headerFont));
                    PdfPCell cell2 = new PdfPCell(new Phrase("Note", headerFont));
                    PdfPCell cell3 = new PdfPCell(new Phrase("Maximum", headerFont));

                    // Alignement des en-têtes
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                    // Ajout des en-têtes au tableau
                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);

                    double totalGrades = 0;
                    double totalMaximum = 0;
                    int countGrades = 0;

                    if (enrollment.getGrades() != null && !enrollment.getGrades().isEmpty()) {
                        for (var grade : enrollment.getGrades()) {
                            // Lignes du tableau
                            PdfPCell evalCell = new PdfPCell(new Phrase(grade.getAssessment().getName(), textFont));
                            PdfPCell gradeCell = new PdfPCell(new Phrase(String.valueOf(grade.getValue()), textFont));
                            PdfPCell maxCell = new PdfPCell(new Phrase(String.valueOf(grade.getAssessment().getMaximum()), textFont));

                            // Alignement des cellules
                            evalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            gradeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            maxCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                            // Ajout des cellules au tableau
                            table.addCell(evalCell);
                            table.addCell(gradeCell);
                            table.addCell(maxCell);

                            totalGrades += grade.getValue();
                            totalMaximum += grade.getAssessment().getMaximum();
                            countGrades++;
                        }
                    } else {
                        PdfPCell noGradesCell = new PdfPCell(new Phrase("Aucune note disponible", textFont));
                        noGradesCell.setColspan(3);
                        noGradesCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(noGradesCell);
                    }

                    document.add(table);

                    // Moyenne du cours
                    if (countGrades > 0 && totalMaximum > 0) {
                        double average = (totalGrades / totalMaximum) * 20; // Calcul sur 20
                        Paragraph averageParagraph = new Paragraph("Moyenne : " + String.format("%.2f", average) + "/20", textFont);
                        document.add(averageParagraph);
                    } else {
                        Paragraph averageParagraph = new Paragraph("Moyenne : N/A", textFont);
                        document.add(averageParagraph);
                    }

                    document.add(new Paragraph(" ")); // Espace
                }
            } else {
                Paragraph noCourses = new Paragraph("Aucun cours assigné.", textFont);
                document.add(noCourses);
            }

            document.close();
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du PDF", e);
        }
    }
}
