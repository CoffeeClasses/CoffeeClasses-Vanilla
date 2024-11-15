package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;

public class StudentService {
	// Singleton
	private static final StudentService INSTANCE = new StudentService();
	private StudentService() {}
	public static StudentService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final UserDAO userDAO = UserDAO.getInstance();

	/*
		Methods
	 */
}
