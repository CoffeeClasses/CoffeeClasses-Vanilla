package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.AssessmentDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;

import java.util.Optional;
import java.util.Set;

public class AssessmentService {
	// Singleton
	private static final AssessmentService INSTANCE = new AssessmentService();
	private AssessmentService() {}
	public static AssessmentService getInstance() {
		return INSTANCE;
	}
	// DAO
	private static final AssessmentDAO assessmentDAO = AssessmentDAO.getInstance();

	/*
	 * Methods
	 */
	public Set<Assessment> getAll() {
		return assessmentDAO.getAll();
	}

	public Optional<Assessment> find(int id) {
		return assessmentDAO.find(id);
	}

	public void save(Assessment assessment) {
		assessmentDAO.save(assessment);
	}
}