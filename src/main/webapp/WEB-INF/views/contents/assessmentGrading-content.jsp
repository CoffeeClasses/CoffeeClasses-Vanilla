<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<h2>Attribution des notes</h2>
<h4 id="asessment-name">${assessment.name}</h4>
<p>Cours concerné: ${assessment.course.name}</p>
<p>Note maximale: ${assessment.maximum}/${assessment.maximum}</p>
<form action="${pageContext.request.contextPath}/grade" method="post">
	<input type="hidden" name="assessmentId" value='${assessment.id}'>
	<table id="gradingTable">
		<tr>
	    	<th>Nom</th>
	    	<th>Prénom</th>
	    	<th>Note</th>
	  	</tr>
	  	<c:if test="${students.isEmpty()}">
	  		<tr>
	  			<td colspan="3">Aucune étudiant n'est inscrit à ce cours</td>
	  		</tr>
	  	</c:if>
	  	<c:forEach var="student" items="${students}">
			<tr>
				<td>${student.firstName}</td>
				<td>${student.lastName}</td>
				<c:set var="gradeFound" value="false"/>
				<c:forEach var="grade" items="${student.grades}">
					<c:choose>
                    	<c:when test="${grade.assessment.id == assessment.id}">
                    		<!-- Already graded student -->
                    		<td><input type="number" min="0" max='${assessment.maximum}' step="0.01" name='${student.id}' value='${grade.value}' required></td>
                    		<c:set var="gradeFound" value="true" />
                        </c:when>
                    </c:choose>
				</c:forEach>
				<c:if test="${!gradeFound}">
                	<!-- Still not graded -->
                    <td><input type="number" min="0" max='${assessment.maximum}' name='${student.id}' required></td>
            	</c:if>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${!students.isEmpty()}">
		<button type="submit" id="gradingButton">Enregistrer les notes</button>
	</c:if>
</form>