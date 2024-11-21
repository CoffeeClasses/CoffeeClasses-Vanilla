package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import java.util.Optional;

public class UserService {
	// Singleton
	private static final UserService INSTANCE = new UserService();
	private UserService() {}
	public static UserService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final UserDAO userDAO = UserDAO.getInstance();

	/* 
	 * Methods
	 */
	public Optional<User> authenticate(String email, String password) {
		// Fetch user by email
		Optional<User> user = userDAO.findByEmail(email);

		// If user exists and passwords match, return user ID
		if (user.isPresent() && user.get().checkPassword(password)) {
			return user;
		}
		return Optional.empty();
	}

	public void register(User user) {
		userDAO.save(user);
	}

	public void unregister(User user) {
		userDAO.delete(user);
	}

	public Optional<User> find(int ID) {
		return userDAO.find(ID);
	}

	public Optional<User> findUserFromIDParameter(Optional<String> userIDString) {
		if (userIDString.isEmpty()) return Optional.empty();

		int userId;
		try {
			userId = Integer.parseInt(userIDString.get());
		} catch (NumberFormatException e) {
			return Optional.empty();
		}

		return this.find(userId);
	}

	public void update(User user) {
		userDAO.update(user);
	}

	public void save(User newUser) {
		userDAO.save(newUser);
	}
}
