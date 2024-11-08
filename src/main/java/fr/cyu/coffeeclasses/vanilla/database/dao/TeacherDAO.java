package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;
public class TeacherDAO extends GenericDAO<Teacher> {
	// Singleton
	private static final TeacherDAO INSTANCE = new TeacherDAO();
	private TeacherDAO() {
		super(Teacher.class);
	}
	public static TeacherDAO getInstance() {
		return INSTANCE;
	}
}
