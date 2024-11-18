package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import org.hibernate.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

public class UserDAO extends GenericDAO<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

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
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
}
