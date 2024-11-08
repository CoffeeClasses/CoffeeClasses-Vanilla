package fr.cyu.coffeeclasses.vanilla.database.daos;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public abstract class GenericDAO<T> {
	private final Class<T> entityClass;
	protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// The entity class is a parameter
	protected GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/*
	 * CRUD operations
	 */
	public void save(T entity) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	public T findById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(entityClass, id);
		}
	}

	public void update(T entity) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.merge(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	public void delete(T entity) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.remove(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	/*
	 * Additional operations
	 */
	public List<T> getAll() {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(entityClass);
			Root<T> root = query.from(entityClass);
			query.select(root);
			return session.createQuery(query).getResultList();
		}
	}
}
