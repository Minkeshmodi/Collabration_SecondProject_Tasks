package com.niit.collaboration.DAOImpl;

import java.util.List;

import javax.management.Query;
import javax.transaction.Transactional;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.DAO.UserDAO;
import com.niit.collaboration.model.User;
@Repository("UserDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
		
	}

	@Transactional
	public List<User> list() {
		
		String hql = "from User";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		
		
		return ((UserDAO) query).list();
	}

	@Transactional
	public boolean save(User user) {
		try{
			sessionFactory.getCurrentSession().save(user);
			
		}catch(Exception e){
			return false;
		}
		
		return true;
	}

	@Transactional
	public boolean update(User user) {
		try{
			sessionFactory.getCurrentSession().update(user);
			
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
		
	

	@Transactional
	public User get(String id) {
		User user = (User)sessionFactory.getCurrentSession().get(User.class,id);
		return user;
	}
	@Transactional
	public User validate(String id, String password) {
		String hql = "from User where id = '" +id+"' and password = '" + password + "'";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		User user = (User)((org.hibernate.Query) query).uniqueResult(); 
		return user;
	}
	
	
}
