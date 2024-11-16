<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.User" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Student" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Administrator" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO" %>
<%
	// Can be used not only for logged in account informations
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
<h1>Mes informations</h1>
<h2>Informations personnels</h2>
<p>Nom: <%= user.getLastName() %></p>
<p>Prénom: <%= user.getFirstName() %></p>
<p>Adresse mail: <%= user.getEmail() %></p>
<p>Date de naissance: <%= user.getBirthDate() %></p>
<%
	if(user instanceof Teacher){
%>		<h2>Mes cours</h2>
<%	}else if(user instanceof Student){
%>		<h2>Mes cours</h2>
		<h2>Mes notes</h2>
<%	} 
%>
