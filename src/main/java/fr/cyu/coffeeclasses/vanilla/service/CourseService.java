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

	public void update(Course course) {
		courseDAO.update(course);
	}

	public void save(Course course) {
		courseDAO.save(course);
	}

	public Optional<Course> findFromIDParameter(Optional<String> IdString) {
		if (IdString.isEmpty()) return Optional.empty();

		int userId;
		try {
			userId = Integer.parseInt(IdString.get());
		} catch (NumberFormatException e) {
			return Optional.empty();
		}

		return this.find(userId);
	}

	public void delete(Course course) {
		courseDAO.delete(course);
	}

	public Set<Course> searchCourses(Optional<String> search) {
		if (search.isPresent()) {
			return courseDAO.search(search.get());
		}
		return courseDAO.getAll();
	}
}
