package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import org.hibernate.Session;
import org.hibernate.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

public class UserDAO extends GenericDAO<User> {
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
	public User findByEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> root = query.from(User.class);

			// Add the condition to filter by email
			query.select(root).where(builder.equal(root.get("email"), email));

			// Execute the query and return the result
			return session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
