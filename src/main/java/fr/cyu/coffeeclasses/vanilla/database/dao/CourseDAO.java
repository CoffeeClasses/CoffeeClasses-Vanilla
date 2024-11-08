package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.element.Course;

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
