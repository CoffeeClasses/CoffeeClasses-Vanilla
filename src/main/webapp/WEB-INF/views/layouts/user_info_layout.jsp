<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.security.InvalidParameterException" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>CoffeeClasses - <%= request.getParameter("title") %></title>
	</head>
	<body>
		<!-- Bannière supérieure -->
		<div class="banner">
			<div class="site-title">
				<img src="${pageContext.request.contextPath}/images/icon.png" alt="Icon" class="icon">
				CoffeeClasses
			</div>
		</div>
		<!-- Display error message if it exists -->
		<c:if test="${not empty errorMessage}">
			<div class="error-message">
					${errorMessage}
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
