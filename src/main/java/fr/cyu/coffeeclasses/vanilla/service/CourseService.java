package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.CourseDAO;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;

import java.util.Optional;
import java.util.Set;

public class CourseService {
	// Singleton
	private static final CourseService INSTANCE = new CourseService();
	private CourseService() {}
	public static CourseService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final CourseDAO courseDAO = CourseDAO.getInstance();

	/* 
	 * Methods
	 */
	public Set<Course> getAll() {
		return courseDAO.getAll();
	}

	public Optional<Course> find(int id) {
		return courseDAO.find(id);
	}
}
