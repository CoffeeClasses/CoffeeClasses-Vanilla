package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.database.dao.TeacherDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;

import java.util.Optional;
import java.util.Set;

public class TeacherService {
	// Singleton
	private static final TeacherService INSTANCE = new TeacherService();
	private TeacherService() {}
	public static TeacherService getInstance() {
		return INSTANCE;
	}
	// DAO
	private final TeacherDAO teacherDAO = TeacherDAO.getInstance();

	/* 
	 * Methods
	 */
	public Optional<Teacher> find(int id) {
		return teacherDAO.find(id);
	}

	public Set<Teacher> getAll() {
		return teacherDAO.getAll();
	}
}
