package com.niit.backend.implementation;


import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import org.hibernate.Session;

import com.niit.backend.dao.UserDao;
import com.niit.backend.model.User;

@Repository(value="userDao")
@Transactional
public class UserDaoImpl implements UserDao{
	
	
	private static final Logger log=LoggerFactory.getLogger(UserDaoImpl.class);
	
	   @Autowired
	   SessionFactory sessionFactory;
	   
	   @Autowired
	   User user;
	   
	   public UserDaoImpl(){
		   
	   }
	    
	    public UserDaoImpl(SessionFactory sessionFactory){
	    	try{
	    		this.sessionFactory=sessionFactory;	
	    	}
	    	catch(Exception e){
	    	  log.error("unable to connect to db");	
	    	 e.printStackTrace();
	    	}
	    	
	    }
		
	   
		@Override
		@Transactional
		public boolean save(User user) {
			log.debug("Calling of the save method");
			try{
				log.debug("opening the new session");
				/*sessionFactory.getCurrentSession().save(user);*/
				Session session=sessionFactory.openSession();
				session.save(user);
				session.flush();
				log.debug("Record Saved Successfully");
				return true;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return false;
			}
			
		}

		@Override
		@Transactional
		public boolean update(User user) {
			log.debug("Calling of the save method");
			try{
				sessionFactory.getCurrentSession().update(user);
				return true;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return false;
			}
			
		
		}

		
		 @Override
		 @Transactional
		public User get(String id) {
			 log.debug("calling of the method save");
			 log.debug("userid is:"+id);
			 return (User)sessionFactory.getCurrentSession().get(User.class,id);
			 
			 
			/*log.debug("starting of the method get  with the id:"+userid);
			String hql="from User where userid='"+ userid + "'";
			log.debug("HQL:"+hql);
			Query query=sessionFactory.openSession().createQuery(hql);
			User user = (User)query.uniqueResult();
			log.debug("userName:"+user.getName());*/
			/*return user;*/
		}

		

		 @Override
		 @Transactional
		public List<User> list() {
		 log.debug("Calling of the getallusers method");
	      String hql="from User";
	      log.debug("hql:"+hql);
	      Query query=sessionFactory.openSession().createQuery(hql);
	      log.debug("The Query is:"+query.getQueryString());
		  return query.list();
		}


		
		@Override
		@Transactional
		public void deleteuserid(String id) {
			try{
				sessionFactory.openSession().delete(id);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		@Transactional
		public User authenticate(String id, String password) {
			log.debug("->->Starting of the method isValidUserDetails");
			String hql = "from User where id= '" + id + "' and " + " password ='" + password+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			log.debug("The Query is:" + hql);
			return (User)query.uniqueResult();
		}

		
		
		
}

