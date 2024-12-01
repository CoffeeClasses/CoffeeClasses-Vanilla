package fr.cyu.coffeeclasses.vanilla.filter;

import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "teacherFilter")
public class TeacherFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Non-teachers, leave !
		User user = (User) request.getAttribute("user");
		if (!(user instanceof Teacher)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/panel/home");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
