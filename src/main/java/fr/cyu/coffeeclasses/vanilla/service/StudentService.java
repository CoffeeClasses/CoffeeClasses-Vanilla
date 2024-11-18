package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.StudentDAO;

public class StudentService {
	// Singleton
	private static final StudentService INSTANCE = new StudentService();
	private StudentService() {}
	public static StudentService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final StudentDAO studentDAO = StudentDAO.getInstance();

	/* 
	 * Methods
	 */
}
