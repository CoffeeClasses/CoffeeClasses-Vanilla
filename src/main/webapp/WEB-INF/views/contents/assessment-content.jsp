<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<h1>Gestion des notes et évaluations</h1>
<h3>Ajouter une évaluation</h3>
<form action="${pageContext.request.contextPath}/assessment" method="post">
	<div class="form-group">
		<label for="cours">Cours à évlauer: </label>
		<select name="selectedCourse" id="selectedCourse" required>
			<c:forEach var="course" items="${courses}">
		        <option value='${course.id}'>${course.name}</option>
		    </c:forEach>
		</select>
	</div>
	<br>
	<div class="form-group">
		<label for="text">Nom de l'évaluation: </label>
		<input type="text" id="name" name="name" required>
	</div>
	<br>
	<div class="form-group">
		<label for="note">Note maximale: </label>
		<input type="number" min="1" id="maxGrade" name="maxGrade" required>
	</div>
	<br>
	<button type="submit">Ajouter</button>
</form>