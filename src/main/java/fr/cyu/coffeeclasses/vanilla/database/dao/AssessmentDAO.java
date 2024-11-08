package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;

public class AssessmentDAO extends GenericDAO<Assessment> {
	// Singleton
	private static final AssessmentDAO INSTANCE = new AssessmentDAO();
	private AssessmentDAO() {
		super(Assessment.class);
	}
	public static AssessmentDAO getInstance() {
		return INSTANCE;
	}
	
}
