package fr.cyu.coffeeclasses.vanilla.listener;

import fr.cyu.coffeeclasses.vanilla.database.dao.AdministratorDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
	// Dependencies
	private final UserService userService = UserService.getInstance();
	private final AdministratorDAO administratorDAO = AdministratorDAO.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Application starting up...");

		// Create default admin if no admins exist
		if (administratorDAO.getAll().isEmpty()) {
			userService.register(
					new Administrator(
							"Default",
							"Admin",
							"admin-coffeeclasses@yopmail.com",
							"admin123",
							LocalDate.of(2003, 10, 9)
					)
			);
			logger.info("Default admin user created.");
		} else {
			logger.info("Admin users already present. Skipping default admin creation.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Application shutting down...");
	}
}
