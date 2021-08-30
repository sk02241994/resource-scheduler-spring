/*
 * package com.office.resourcescheduler.dao;
 * 
 * import java.util.List;
 * 
 * import javax.persistence.criteria.CriteriaQuery; import
 * javax.transaction.Transactional;
 * 
 * import org.hibernate.Session; import org.hibernate.SessionFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.office.resourcescheduler.model.User;
 * 
 * @Repository
 * 
 * @Transactional public class UserDao {
 * 
 * @Autowired private SessionFactory sessionFactory;
 * 
 * public Session getSession() { Session session =
 * sessionFactory.getCurrentSession(); if (session == null) { session =
 * sessionFactory.openSession(); } return session; }
 * 
 * public void saveUser(User user) { getSession().saveOrUpdate(user); }
 * 
 * public List<User> getUsers() { CriteriaQuery<User> criteriaQuery =
 * getSession().getCriteriaBuilder().createQuery(User.class);
 * criteriaQuery.from(User.class); return
 * getSession().createQuery(criteriaQuery).getResultList(); } }
 */