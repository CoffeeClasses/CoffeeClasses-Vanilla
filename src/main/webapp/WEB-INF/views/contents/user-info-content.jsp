<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.User" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO" %>

<%
// Can be used not only for logged in account informations
User u = UserDAO.getInstance().findById(Integer.parseInt(request.getParameter("userId"))); 
%>

<h1>Mes informations</h1>
<h2>Informations personnels</h2>
<p>Nom: <%= u.getLastName() %></p>
<p>Prénom: <%= u.getFirstName() %></p>
<p>Adresse mail: <%= u.getEmail() %></p>
<p>Date de naissance: <%= u.getBirthDate() %></p>
<h2>Mes cours</h2>