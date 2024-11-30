<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="target" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.Student" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Résumé et Graphique</title>
    <c:if test="${param.view == 'chart'}">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </c:if>
</head>
<body>
<div class="box">
    <h1>${target.firstName} ${target.lastName}</h1>

    <h2>Notes avec graphique</h2>

    <c:choose>
        <c:when test="${not empty target.enrollments}">
            <ul>
                <c:forEach var="enrollment" items="${target.enrollments}">
                    <li><strong>${enrollment.course.name}</strong></li>
                    <ul>
                        <c:forEach var="grade" items="${enrollment.grades}">
                            <li>${grade.assessment.name} : ${grade.value} / ${grade.assessment.maximum}</li>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </ul>

            <!-- Section graphique uniquement si demandé -->
            <c:if test="${param.view == 'chart'}">
                <canvas id="gradesChart" width="400" height="200"></canvas>
                <script>
                    // Préparation des données pour le graphique
                    const labels = [
                        <c:forEach var="enrollment" items="${target.enrollments}">
                            <c:forEach var="grade" items="${enrollment.grades}">
                                '${grade.assessment.name}',
                            </c:forEach>
                        </c:forEach>
                    ];
                    const data = {
                        labels: labels,
                        datasets: [{
                            label: 'Notes obtenues',
                            data: [
                                <c:forEach var="enrollment" items="${target.enrollments}">
                                    <c:forEach var="grade" items="${enrollment.grades}">
                                        ${grade.value},
                                    </c:forEach>
                                </c:forEach>
                            ],
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    };

                    // Configuration du graphique
                    const config = {
                        type: 'bar',
                        data: data,
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    max: 20
                                }
                            }
                        }
                    };

                    // Affichage du graphique
                    const gradesChart = new Chart(
                        document.getElementById('gradesChart'),
                        config
                    );
                </script>
            </c:if>
        </c:when>
        <c:otherwise>
            <p>Aucune donnée disponible pour cet étudiant.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
