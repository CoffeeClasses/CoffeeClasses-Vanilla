package fr.cyu.coffeeclasses.vanilla.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/panel*")
public class UserFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		if (session != null) {
			// Transfer session data to attributes
			request.setAttribute("userType", session.getAttribute("userType"));
			request.setAttribute("username", session.getAttribute("username"));
		}
		// Continue with the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
