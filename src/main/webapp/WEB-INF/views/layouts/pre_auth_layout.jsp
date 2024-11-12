<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="java.security.InvalidParameterException" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>CoffeeClasses - <%= request.getParameter("title") %></title>
		<link rel="stylesheet" href="<c:url value='/css/main.css'/>">
		<link rel="stylesheet" href="<c:url value='/css/pre-auth.css'/>">
		<!-- This is a workaround due to \${} issues currently happening with the project. -->
		<link rel="stylesheet" href="./css/pages/<%= request.getParameter("contentPage") %>.css">
	</head>
	<body>
		<!-- Bannière supérieure -->
		<div class="banner">
			<div class="site-title">
				<img src="<c:url value='/images/icon.png'/>" alt="Icon" class="icon">
				CoffeeClasses
			</div>
		</div>

		<!-- Display error message if it exists -->
		<c:if test="${not empty errorMessage}">
			<div class="error-message">
					<%= request.getAttribute("errorMessage") %>
			</div>
		</c:if>

		<!-- Include the page-specific content -->
		<div class="auth-container">
			<div class="auth-content">
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
			</div>
		</div>
	</body>
</html>
