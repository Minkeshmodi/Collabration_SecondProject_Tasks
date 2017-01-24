package com.niit.backend.implementation;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.backend.dao.BlogDao;
import com.niit.backend.model.Blog;

@Repository("BlogDao")
public class BlogDaoImpl implements BlogDao {
	private static final Logger log = LoggerFactory.getLogger(BlogDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public BlogDaoImpl(){
		
	}

	public BlogDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Blog> getAllBlogs() {
		List<Blog> allBlogs = null;
		try{
			
			log.debug("Method => getAllBlogs() execution is starting");
			allBlogs = sessionFactory.getCurrentSession().createQuery("FROM UserBlog").list();
			if(allBlogs==null || allBlogs.isEmpty()){
				log.debug("Record not found in UserBlog table");
			}
		}
		catch(HibernateException ex){
			log.debug("Fetch Error :" + ex.getMessage());
			ex.printStackTrace();
		}
		return allBlogs;
	}

	@Override
	@Transactional
	public boolean saveUserBlog(Blog ubObj) {
		try
		{
			log.debug("Method => saveBlog() execution is starting");
			Session session=sessionFactory.openSession();
			session.save(ubObj);
			session.flush();
		/*	sessionFactory.getCurrentSession().saveOrUpdate(ubObj);*/
			return true;
		}
		catch(HibernateException ex){
			log.debug("Data Save Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateApprove(int blgid, char flag) {
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update UserBlog set Approve = '" + flag + "' where id = " + blgid);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data update Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public Blog getBlogByID(int blgid) {
		try
		{
			log.debug("Method => getBlogByID() execution is starting");
			return (Blog) sessionFactory.getCurrentSession().get(Blog.class, blgid);
		}
		catch(HibernateException ex){
			log.debug("Data fetch Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public boolean getUpdateLike(int blgid) {
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update UserBlog set likes = likes + 1 where id = " + blgid);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data update Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean getDelete(int blgid) {
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update UserBlog set Approve = 'D' where id = " + blgid);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data update Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

	}
	

}
