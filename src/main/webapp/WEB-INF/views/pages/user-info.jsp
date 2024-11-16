<jsp:include page="/WEB-INF/views/layouts/user_info_layout.jsp">
  <jsp:param name="title" value="User-info" />
  <jsp:param name="contentPage" value="user-info" />
  <jsp:param name="userId" value='<%= session.getAttribute("userId") %>'/>
</jsp:include>