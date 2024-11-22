<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="teachers" scope="request" type="java.util.Set<fr.cyu.coffeeclasses.vanilla.entity.user.Teacher>"/>

<div class="form-container">
	<h2>Ajouter un nouveau cours</h2>
	<form action="${pageContext.request.contextPath}/panel/admin/courses/add" method="post">
		<div class="form-group">
			<label for="name">Nom du cours :</label>
			<input type="text" id="name" name="name" placeholder="Entrez le nom du nouveau cours" required>
		</div>

		<div class="form-group">
			<label for="teacherId">Assigner un professeur :</label>
			<select id="teacherId" name="teacherId" required>
				<c:forEach var="teacher" items="${teachers}">
					<option value="${teacher.id}">${teacher.firstName} ${teacher.lastName}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-primary">Ajouter un cours</button>
	</form>
</div>