package fr.cyu.coffeeclasses.vanilla.listener;

import fr.cyu.coffeeclasses.vanilla.database.dao.AdministratorDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO;
import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
import fr.cyu.coffeeclasses.vanilla.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
	private final UserService userService = UserService.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Application starting up...");

		// Create default admin if no admins exist
		if (AdministratorDAO.getInstance().getAll().isEmpty()) {
			userService.register(
					new Administrator(
							"Default",
							"Admin",
							"teacher.coffeeclasses@yopmail.com",
							"admin123",
							LocalDate.of(2003, 10, 9)
					)
			);
			System.out.println("Default admin user created.");
		} else {
			System.out.println("Admin users already present.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Application shutting down...");
	}
}
