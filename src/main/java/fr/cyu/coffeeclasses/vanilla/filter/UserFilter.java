package fr.cyu.coffeeclasses.vanilla.filter;

import fr.cyu.coffeeclasses.vanilla.database.exception.DataNonsenseException;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;
import fr.cyu.coffeeclasses.vanilla.service.UserService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "userFilter")
public class UserFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		// Check if user is logged in
		if (session == null || session.getAttribute("userId") == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			return;
		}

		// Transfer user to request attributes for convenience
		User user = UserService.getInstance().find((Integer) session.getAttribute("userId")).orElseThrow(() -> new DataNonsenseException("User not found while requesting for info."));
		request.setAttribute("user", user);

		// Continue the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
