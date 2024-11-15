package fr.cyu.coffeeclasses.vanilla.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import fr.cyu.coffeeclasses.vanilla.service.UserService;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
	private final UserService userService = UserService.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Application starting up...");
		userService.createDefaultAdmin(); // Ensure default admin user exists
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Application shutting down...");
	}
}
