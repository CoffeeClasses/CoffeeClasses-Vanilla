<%
	if (session != null && session.getAttribute("userId") != null) {
		response.sendRedirect(pageContext.getRequest().getServletContext().getContextPath() + "/panel/home");
	} else {
		response.sendRedirect(pageContext.getRequest().getServletContext().getContextPath() + "/login");
	}
%>
