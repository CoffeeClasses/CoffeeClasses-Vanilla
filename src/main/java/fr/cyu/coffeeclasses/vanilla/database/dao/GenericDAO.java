package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	private final Class<T> entityClass;
	protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// The entity class is a parameter
	protected GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/*
	 * CRUD operations
	 */
	public void save(T entity) throws DataAccessException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while saving entity: {}", entity, e);
			throw new DataAccessException("Error while saving entity", e);
		}
	}

	public Optional<T> findById(int id) throws DataAccessException {
		try (Session session = sessionFactory.openSession()) {
			return Optional.ofNullable(session.get(entityClass, id));
		} catch (Exception e) {
			logger.error("Error while finding entity by ID: {}", id, e);
			throw new DataAccessException("Error while finding entity by ID", e);
		}
	}

	public void update(T entity) throws DataAccessException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.merge(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while updating entity: {}", entity, e);
			throw new DataAccessException("Error while updating entity", e);
		}
	}

	public void delete(T entity) throws DataAccessException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.remove(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while deleting entity: {}", entity, e);
			throw new DataAccessException("Error while deleting entity", e);
		}
	}

	/*
	 * Additional operations
	 */
	public List<T> getAll() throws DataAccessException {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(entityClass);
			Root<T> root = query.from(entityClass);
			query.select(root);
			return session.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error while retrieving all entities", e);
			throw new DataAccessException("Error while retrieving all entities", e);
		}
	}
}
