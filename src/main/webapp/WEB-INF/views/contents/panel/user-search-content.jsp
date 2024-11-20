<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="search-users">
	<h1>Rechercher un utilisateur</h1>
	<form action="${pageContext.request.contextPath}/panel/admin/users" method="get">
		<div>
			<label for="role">Rôle :</label>
			<select name="role" id="role">
				<option value="">Tous</option>
				<option value="student">Étudiants</option>
				<option value="teacher">Professeurs</option>
				<option value="administrator">Administrateurs</option>
			</select>
		</div>
		<div>
			<label for="search">Nom ou e-mail :</label>
			<input type="text" id="search" name="search" placeholder="Entrez un nom ou une adresse mail">
		</div>
		<div>
			<button type="submit">Rechercher</button>
		</div>
	</form>

	<jsp:useBean id="users" scope="request" type="java.util.Set<fr.cyu.coffeeclasses.vanilla.entity.user.User>"/>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.User" %>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.database.exception.DataNonsenseException" %>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher" %>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Student" %>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Administrator" %>
	<%
		if (!users.isEmpty()) { %>
			<h2>Résultats</h2>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nom</th>
						<th>E-mail</th>
						<th>Rôle</th>
					</tr>
				</thead>
				<tbody>
					<% for (User user : users) { %>
						<tr>
							<td><%= user.getId().orElseThrow(() -> new DataNonsenseException("Uninitialized user " + user.getEmail() + " in database ?")) %></td>
							<td><%= user.getFirstName() + " " + user.getLastName() %></td>
							<td><%= user.getEmail() %></td>
							<!-- Role -->
							<td>
								<% if (user instanceof Student) { %>Étudiant
								<% } else if (user instanceof Teacher) { %>Professeur
								<% } else if (user instanceof Administrator) { %>Administrateur
								<% } else { throw new DataNonsenseException("User " + user.getId() + "  is not using a valid role."); } %>
							</td>
						</tr>
					<% } %>
				</tbody>
			</table>
	<% } %>
</div>
