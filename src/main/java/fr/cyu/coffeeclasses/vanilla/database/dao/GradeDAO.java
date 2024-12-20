package fr.cyu.coffeeclasses.vanilla.database.dao;

import java.util.Optional;

import fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;
import fr.cyu.coffeeclasses.vanilla.entity.user.Student;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class GradeDAO extends GenericDAO<Grade> {
	// Singleton
	private static final GradeDAO INSTANCE = new GradeDAO();
	private GradeDAO() {
		super(Grade.class);
	}
	public static GradeDAO getInstance() {
		return INSTANCE;
	}
	
	public Optional<Grade> findByStudentAndAssessment(Student student, Assessment assessment){
		try (Session session = sessionFactory.openSession()) {
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Grade> cr = cb.createQuery(Grade.class);
            Root<Grade> root = cr.from(Grade.class);

			// Join with Enrollment to access the Student entity
			Root<Enrollment> enrollmentRoot = cr.from(Enrollment.class);

			// Define the query criteria
			cr.select(root).where(
					cb.and(
							cb.equal(root.get("assessment"), assessment),
							cb.equal(root.get("enrollment"), enrollmentRoot),
							cb.equal(enrollmentRoot.get("student"), student)
					)
			);
            
            Query<Grade> query = session.createQuery(cr);
            return query.uniqueResultOptional();
		} catch (Exception e) {
			throw new DataAccessException("Error while finding entity by ID", e);
		}
	}
}