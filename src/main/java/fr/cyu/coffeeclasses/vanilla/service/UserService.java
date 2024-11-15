package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import java.time.LocalDate;
import java.util.Optional;

public class UserService {
	private static final UserService INSTANCE = new UserService();
	private final UserDAO userDAO = UserDAO.getInstance();

	private UserService() {}

	public static UserService getInstance() {
		return INSTANCE;
	}

	/*
		Methods
	 */
	public Optional<Integer> authenticate(String email, String password) {
		// Fetch user by email
		User user = userDAO.findByEmail(email);

		// If user exists and passwords match, return user ID
		if (user != null && user.checkPassword(password)) {
			return Optional.of(user.getId());
		}
		return Optional.empty();
	}

	public void register(User user) {
		userDAO.save(user);
	}
}
