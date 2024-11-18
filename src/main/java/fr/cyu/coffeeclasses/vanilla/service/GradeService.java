package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.GradeDAO;

public class GradeService {
	// Singleton
	private static final GradeService INSTANCE = new GradeService();
	private GradeService() {}
	public static GradeService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final GradeDAO gradeDAO = GradeDAO.getInstance();

	/* 
	 * Methods
	 */
}
