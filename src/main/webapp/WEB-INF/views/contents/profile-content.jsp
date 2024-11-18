<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Mes informations</h1>

<h2>Informations personnelles</h2>
<p><strong>Nom :</strong> ${user.lastName}</p>
<p><strong>Prénom :</strong> ${user.firstName}</p>
<p><strong>Adresse e-mail :</strong> ${user.email}</p>
<p><strong>Date de naissance :</strong> ${user.birthDate}</p>

<c:if test="${userType == 'Teacher'}">
	<h2>Cours</h2>
	<c:choose>
		<c:when test="${not empty user.courses}">
			<ul>
				<c:forEach var="course" items="${user.courses}">
					<li>${course.name}</li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p class="empty">Aucun cours assigné.</p>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${userType == 'Student'}">
	<h2>Cours et évaluations</h2>
	<c:choose>
		<c:when test="${not empty user.enrollments}">
			<c:forEach var="enrollment" items="${user.enrollments}">
				<div class="course-box">
					<h3>${enrollment.course.name}</h3>
					<ul>
						<c:choose>
							<c:when test="${not empty enrollment.grades}">
								<c:forEach var="grade" items="${enrollment.grades}">
									<li>${grade.assessment.name}: ${grade.value}/${grade.assessment.maximum}</li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="empty">Aucune note disponible.</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p class="empty">Aucun cours assigné.</p>
		</c:otherwise>
	</c:choose>
</c:if>
