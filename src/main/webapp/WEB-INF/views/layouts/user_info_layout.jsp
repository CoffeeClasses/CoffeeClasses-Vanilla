<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.security.InvalidParameterException" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.User" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Student" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Administrator" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Course" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Grade" %>
<%@ page import="java.util.Set"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>CoffeeClasses - <%= request.getParameter("title") %></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pre-auth.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/<%= request.getParameter("contentPage") %>.css">
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
		<div class="info-container">
			<div class="info-content">
				<%
					String contentPage = request.getParameter("contentPage");
					if (contentPage != null) {
						out.flush();
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/contents/" + contentPage + "-content.jsp");
						dispatcher.include(request, response);
						User user = UserDAO.getInstance().findById(Integer.parseInt(request.getParameter("userId")));
						try{
							user = (Administrator) user;
						}catch(ClassCastException notAdmin){
							try{
								user = (Teacher) user;
							}catch(ClassCastException notTeacher){
								try{
									user = (Student) user;
								}catch(ClassCastException notStudent){
								}
							}
						}
						%>
						<p>Nom: <%= user.getLastName() %></p>
						<p>Prénom: <%= user.getFirstName() %></p>
						<p>Adresse mail: <%= user.getEmail() %></p>
						<p>Date de naissance: <%= user.getBirthDate() %></p>
						<%
						if(user instanceof Teacher){
							Teacher t = (Teacher) user;
							%><h2>Cours</h2><%
							if(t.getCourses().size()>0){
								for(Course c: t.getCourses()){
									%><p> <%=c.getName() %> </p><%
								}
							}else{
								%><p class="empty">Aucun cours ne vous est assigné</p><%
							}
						}else if(user instanceof Student){
							Student s = (Student) user;
							%><h2>Cours et évaluations</h2><%
							if(s.getEnrollments().size()>0){
								Course course;
								Set<Grade> grades;
								for(Enrollment e: s.getEnrollments()){
									course = e.getCourse();
									grades = s.getGradesByCourse(course);
									%>
									<div class="courseBox">
										<p><%=course.getName()%></p>
										<ul>
											<% 
											if(grades.size()>0){
												for(Grade g : grades){ %>
													<li><%=g.getAssessment().getName()%>: <%=g.getValue()%>/<%=g.getAssessment().getMaximum() %></li>
											<%	}
											}else{ %>
												<li class="empty">Aucune note</li>
											<%} %>
										</ul>
									</div>
									<%
								}
							}else{
								%><p class="empty">Vous n'êtes inscrit à aucun cours</p><%
							}
						}
					} else {
						throw new InvalidParameterException("Pre-Auth layout requested with no content.");
					}
				%>
			</div>
		</div>
	</body>
</html>
