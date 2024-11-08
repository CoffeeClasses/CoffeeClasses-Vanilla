<%
	if (session != null && session.getAttribute("userId") != null) {
		response.sendRedirect("/panel");
	} else {
		response.sendRedirect("/login");
	}
%>
