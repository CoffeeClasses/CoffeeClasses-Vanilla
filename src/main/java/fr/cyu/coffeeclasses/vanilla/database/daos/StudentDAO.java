package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.users.Student;

public class StudentDAO extends GenericDAO<Student> {
	// Singleton
	private static final StudentDAO INSTANCE = new StudentDAO();
	private StudentDAO() {
		super(Student.class);
	}
	public static StudentDAO getInstance() {
		return INSTANCE;
	}
}
