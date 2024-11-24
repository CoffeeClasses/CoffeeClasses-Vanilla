package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.GradeDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import java.util.Optional;

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
	public void save(Grade grade) {
		gradeDAO.save(grade);
	}

	public void update(Grade grade) {

		gradeDAO.update(grade);
	}

	public Optional<Grade> findByStudentAndAssessment(Student s, Assessment assessment) {
		return gradeDAO.findByStudentAndAssessment(s, assessment);
	}
}
