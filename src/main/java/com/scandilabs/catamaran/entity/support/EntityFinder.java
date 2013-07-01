package com.scandilabs.catamaran.entity.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class EntityFinder {
	
	private SessionFactory sessionFactory;
	private Class clazz;
	
	public EntityFinder(SessionFactory sessionFactory, Class clazz) {
		this.sessionFactory = sessionFactory;
		this.clazz = clazz;
	}

	/**
	 * Retrieves all persistent objects of a given type
	 * 
	 * @return
	 */
	public <T> List<T> all() {
		return Collections.checkedList(PersistableUtils.all(this.sessionFactory, this.clazz), this.clazz);
	}
	
	/**
	 * Retrieves a list of persistent objects that match the given filter
	 * parameters
	 * 
	 * @param queryParameters
	 * @return
	 */
	public <T> List<T> filter(Map<String, Object> queryParameters) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, queryParameters), this.clazz);
	}
	
	/**
	 * Retrieves a list of persistent objects that match the given property name and value
	 * @param name
	 * @param value
	 * @return
	 */
	public <T> List<T> filter(String name, String value) {
		Map<String, Object> queryParameters = new HashMap<String, Object>();
		queryParameters.put(name, value);
		return filter(queryParameters);
	}
	
	/**
	 * Retrieves a list of persistent objects that matches the given hibernate filter Criteria Restrictions
	 * parameters
	 * 
	 * @param queryParameters
	 * @return
	 */
	public <T> List<T> filter(Set<Criterion> criteria) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criteria), this.clazz);
	}
	
	public <T> List<T> filter(Set<Criterion> criteria, Order order) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criteria, order), this.clazz);
	}

	public <T> List<T> filter(Criterion criterion, Order order) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criterion, order), this.clazz);
	}
	
	public <T> List<T> filter(Criterion criterion) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criterion), this.clazz);
	}
	
	public <T> List<T> order(Order order) {
		return Collections.checkedList(PersistableUtils.order(this.sessionFactory, this.clazz, order), this.clazz);
	}

	public <T> List<T> order(List<Order> orders) {
		return Collections.checkedList(PersistableUtils.order(this.sessionFactory, this.clazz, orders), this.clazz);
	}
	
	public <T> List<T> filter(Criterion criterion, List<Order> orders) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criterion, orders), this.clazz);
	}

	public <T> List<T> filter(Set<Criterion> criteria, List<Order> orders) {
		return Collections.checkedList(PersistableUtils.filter(this.sessionFactory, this.clazz, criteria, orders), this.clazz);
	}

	/**
	 * Loads an instance by primary key * @param clazz - the class of the
	 * desired object
	 * 
	 * @param id
	 *            - the identifier of desired object
	 * @return 
	 * @throws RepositoryObjectNotFoundException
	 *             if object not found
	 * 
	 * @return
	 */
	public <T> T load(Long id) {
		return (T) PersistableUtils.loadPersistentEntity(this.sessionFactory, clazz, id);
	}

	/**
	 * Gets an instance by primary key * @param clazz - the class of the desired
	 * object
	 * 
	 * @param id
	 *            - the identifier of desired object
	 * @return PersistableBase object, or null if none found
	 */
	public <T> T get(Long id) {
		return (T) PersistableUtils.getPersistentEntity(this.sessionFactory, clazz, id);
	}
	
	/**
	 * Make sessionFactory available to subclasses so that they can implement their own complex queries
	 * @return
	 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Convenience method for use by subclasses that implement their own queries
	 * @return
	 */
	protected Session getSession() {
		Session session = PersistableUtils.getHibernateSession(getSessionFactory());
		return session;
	}

	
}
