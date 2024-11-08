package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;

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