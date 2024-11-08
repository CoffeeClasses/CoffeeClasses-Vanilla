package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment;

public class EnrollmentDAO extends GenericDAO<Enrollment> {
	// Singleton
	private static final EnrollmentDAO INSTANCE = new EnrollmentDAO();
	private EnrollmentDAO() {
		super(Enrollment.class);
	}
	public static EnrollmentDAO getInstance() {
		return INSTANCE;
	}
}