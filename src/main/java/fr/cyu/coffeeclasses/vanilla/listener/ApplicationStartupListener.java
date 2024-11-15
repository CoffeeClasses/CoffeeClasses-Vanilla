package fr.cyu.coffeeclasses.vanilla.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;

import java.time.LocalDate;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
	private final UserService userService = UserService.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Application starting up...");
		// Create default users if they don't exist.
		String adminEmail = "admin@example.com";
		if (UserDAO.getInstance().findByEmail(adminEmail) == null) {
			UserService.getInstance().register(
				Administrator.createAdmin(
					"Default",
					"Admin",
					adminEmail,
					"admin123",
					LocalDate.of(2003, 10, 9)
				)
			);
			System.out.println("Default admin user created with email: " + adminEmail);
		} else {
			System.out.println("Default admin user already exists.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Application shutting down...");
	}
}
