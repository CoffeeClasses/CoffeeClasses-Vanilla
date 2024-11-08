package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.entities.elements.Course;

public class CourseDAO extends GenericDAO<Course> {
	// Singleton
	private static final CourseDAO INSTANCE = new CourseDAO();
	private CourseDAO() {
		super(Course.class);
	}
	public static CourseDAO getInstance() {
		return INSTANCE;
	}
}
