package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;

import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class CourseDAO extends GenericDAO<Course> {
	private static final Logger logger = LoggerFactory.getLogger(CourseDAO.class);

	// Singleton
	private static final CourseDAO INSTANCE = new CourseDAO();
	private CourseDAO() {
		super(Course.class);
	}
	public static CourseDAO getInstance() {
		return INSTANCE;
	}

	public Set<Course> search(String search) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Course> query = builder.createQuery(Course.class);
			Root<Course> root = query.from(Course.class);

			List<Predicate> predicates = new ArrayList<>();

			// Filter by search term
			String searchPattern = "%" + search + "%";
				predicates.add(builder.or(
						builder.like(root.get("name"), searchPattern),  // Search in course name
						builder.like(root.get("teacher").get("firstName"), searchPattern),  // Search in teacher first name
						builder.like(root.get("teacher").get("lastName"), searchPattern)  // Search in teacher last name
				));

			query.where(builder.and(predicates.toArray(new Predicate[0])));

			List<Course> result = session.createQuery(query).getResultList();
			return new HashSet<>(result);
		} catch (Exception e) {
			logger.error("Error while finding courses : {}", search, e);
			throw new DataAccessException("Error while finding courses : " + search, e);
		}
	}
}
