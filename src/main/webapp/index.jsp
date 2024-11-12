<%
	if (session != null && session.getAttribute("userId") != null) {
		response.sendRedirect(pageContext.getRequest().getServletContext().getContextPath() + "/panel");
	} else {
		response.sendRedirect(pageContext.getRequest().getServletContext().getContextPath() + "/login");
	}
%>
