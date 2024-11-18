package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;

public class TeacherService {
	// Singleton
	private static final TeacherService INSTANCE = new TeacherService();
	private TeacherService() {}
	public static TeacherService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final TeacherDAO teacherDAO = TeacherDAO.getInstance();

	/* 
	 * Methods
	 */
}
