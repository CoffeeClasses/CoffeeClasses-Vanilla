package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.elements.Enrollment;

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