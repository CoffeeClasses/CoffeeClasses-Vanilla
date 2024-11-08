package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.elements.Grade;

public class GradeDAO extends GenericDAO<Grade> {
	// Singleton
	private static final GradeDAO INSTANCE = new GradeDAO();
	private GradeDAO() {
		super(Grade.class);
	}
	public static GradeDAO getInstance() {
		return INSTANCE;
	}
}