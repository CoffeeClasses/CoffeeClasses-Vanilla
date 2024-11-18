package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;

public class CourseService {
	// Singleton
	private static final CourseService INSTANCE = new CourseService();
	private CourseService() {}
	public static CourseService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final CourseDAO courseDAO = CourseDAO.getInstance();

	/* 
	 * Methods
	 */
}
