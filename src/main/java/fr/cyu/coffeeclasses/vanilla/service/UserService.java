package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;

import java.time.LocalDate;

public class UserService {
	private static final UserService INSTANCE = new UserService();
	private final UserDAO userDAO = UserDAO.getInstance();

	private UserService() {}

	public static UserService getInstance() {
		return INSTANCE;
	}

	// Temporary ?
	public void createDefaultAdmin() {
		String adminEmail = "admin@example.com";
		if (userDAO.findByEmail(adminEmail) == null) {
			Administrator admin = Administrator.createAdmin(
					"Default",
					"Admin",
					adminEmail,
					"admin123",
					LocalDate.of(2003, 10, 9)
			);
			userDAO.save(admin);
			System.out.println("Default admin user created with email: " + adminEmail);
		} else {
			System.out.println("Default admin user already exists.");
		}
	}
}
