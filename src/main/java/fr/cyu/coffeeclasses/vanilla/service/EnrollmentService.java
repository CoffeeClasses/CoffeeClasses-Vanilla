package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.EnrollmentDAO;

public class EnrollmentService {
	// Singleton
	private static final EnrollmentService INSTANCE = new EnrollmentService();
	private EnrollmentService() {}
	public static EnrollmentService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final EnrollmentDAO enrollmentDAO = EnrollmentDAO.getInstance();

	/* 
	 * Methods
	 */
}
