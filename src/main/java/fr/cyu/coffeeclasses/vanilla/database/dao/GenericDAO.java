package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.database.HibernateUtil;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataAccessException;
import fr.cyu.coffeeclasses.vanilla.database.exception.DataUpdateException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
	public void save(T entity) throws DataUpdateException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while saving entity: {}", entity, e);
			throw new DataUpdateException("Error while saving entity", e);
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

	public void update(T entity) throws DataUpdateException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.merge(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while updating entity: {}", entity, e);
			throw new DataUpdateException("Error while updating entity", e);
		}
	}

	public void delete(T entity) throws DataUpdateException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.remove(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			logger.error("Error while deleting entity: {}", entity, e);
			throw new DataUpdateException("Error while deleting entity", e);
		}
	}

	/*
	 * Additional operations
	 */
	public Set<T> getAll() throws DataAccessException {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(entityClass);
			Root<T> root = query.from(entityClass);
			query.select(root);
			return new HashSet<>(session.createQuery(query).getResultList());
		} catch (Exception e) {
			logger.error("Error while retrieving all entities", e);
			throw new DataAccessException("Error while retrieving all entities", e);
		}
	}

	public void deleteById(int id) throws DataAccessException, DataUpdateException {
		Optional<T> entity = findById(id);
		if (entity.isPresent()) {
			delete(entity.get());
		} else {
			logger.error("Could not find entity to delete by ID : {}", id);
			throw new DataAccessException("Could not locate entity to delete by ID : " + id);
		}
	}
}
