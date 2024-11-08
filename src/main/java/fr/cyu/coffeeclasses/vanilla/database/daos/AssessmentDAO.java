package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.elements.Assessment;

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
