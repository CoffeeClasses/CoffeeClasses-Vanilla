<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.security.InvalidParameterException" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" href="${pageContext.request.contextPath}/svg/logo.svg">
	<title>CoffeeClasses - <%= request.getParameter("title") %></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/panel.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/<%= request.getParameter("contentPage") %>.css">
</head>
<body>
	<header class="panel-header">
		<!-- Bannière -->
		<div class="project-title">
			<img src="${pageContext.request.contextPath}/svg/logo.svg" alt="Logo" class="project-logo">
			<i class="project-name">CoffeeClasses</i>
		</div>

		<!-- Liens de navigation -->
		<div class="panel-links">
			<a href="${pageContext.request.contextPath}/panel">Accueil</a>
			<c:choose>
				<c:when test="${userType == 'Administrator'}">
					<a href="${pageContext.request.contextPath}/admin/users">Utilisateurs</a>
					<a href="${pageContext.request.contextPath}/admin/courses">Cours</a>
				</c:when>
				<c:when test="${userType == 'Teacher'}">
					<a href="${pageContext.request.contextPath}/teacher/courses">Mes cours</a>
					<a href="${pageContext.request.contextPath}/teacher/grades">Mes évaluations</a>
				</c:when>
				<c:when test="${userType == 'Student'}">
					<a href="${pageContext.request.contextPath}/student/courses">Mes cours</a>
					<a href="${pageContext.request.contextPath}/student/grades">Mes résultats</a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<a href="${pageContext.request.contextPath}/profile">Profil</a>
		</div>

		<!-- Nom d'utilisateur et déconnexion -->
		<div class="user-actions">
			<span class="username">${username}</span>
			<form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
				<button type="submit" class="logout-button">Logout</button>
			</form>
		</div>
	</header>
		<!-- Display error message if it exists -->
		<c:if test="${not empty errorMessage}">
			<div class="error-message">
					${errorMessage}
			</div>
		</c:if>
		<!-- Display success message if it exists -->
		<c:if test="${not empty successMessage}">
			<div class="success-message">
					${successMessage}
			</div>
		</c:if>
	<main>
		<%
			String contentPage = request.getParameter("contentPage");
			if (contentPage != null) {
				out.flush();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/contents/" + contentPage + "-content.jsp");
				dispatcher.include(request, response);
			} else {
				throw new InvalidParameterException("Pre-Auth layout requested with no content.");
			}
		%>
	</main>
</body>