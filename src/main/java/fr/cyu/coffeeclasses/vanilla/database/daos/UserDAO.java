package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.users.User;

public class UserDAO extends GenericDAO<User> {
	// Singleton
	private static final UserDAO INSTANCE = new UserDAO();
	private UserDAO() {
		super(User.class);
	}
	public static UserDAO getInstance() {
		return INSTANCE;
	}
}
