package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class UserDAO extends GenericDAO<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// Singleton
	private static final UserDAO INSTANCE = new UserDAO();
	private UserDAO() {
		super(User.class);
	}
	public static UserDAO getInstance() {
		return INSTANCE;
	}

	/*
		Methods
	 */
	public Optional<User> findByEmail(String email) throws DataAccessException {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> root = query.from(User.class);
			query.select(root).where(builder.equal(root.get("email"), email));
			return Optional.ofNullable(session.createQuery(query).uniqueResult());
		} catch (Exception e) {
			logger.error("Error while finding user by email: {}", email, e);
			throw new DataAccessException("Error while finding user by email: " + email, e);
		}
	}

	public Set<User> searchUsers(Optional<Class<? extends User>> role, Optional<String> search) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> root = query.from(User.class);

			List<Predicate> predicates = new ArrayList<>();

			// Filter by role
			role.ifPresent(r -> predicates.add(builder.equal(root.type(), r)));

			// Filter by search term
			search.ifPresent(value -> {
				String searchPattern = "%" + value + "%";
				predicates.add(builder.or(
					builder.like(root.get("firstName"), searchPattern),
					builder.like(root.get("lastName"), searchPattern),
					builder.like(root.get("email"), searchPattern)
				));
			});

			query.where(builder.and(predicates.toArray(new Predicate[0])));

			List<User> result = session.createQuery(query).getResultList();
			return new HashSet<>(result);
		} catch (Exception e) {
			logger.error("Error while finding user : {}", search, e);
			throw new DataAccessException("Error while finding user : " + search, e);
		}
	}

}
