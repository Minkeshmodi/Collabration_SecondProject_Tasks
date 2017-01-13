package com.niit.backend.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.JobDao;
import com.niit.backend.model.Job;
import com.niit.backend.model.JobApplication;

@Repository(value="JobDao")
public class JobDaoImpl implements JobDao {
	
	private static final Logger log = LoggerFactory.getLogger(JobDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public JobDaoImpl(SessionFactory sessionFactory){
		try{
			this.sessionFactory = sessionFactory;
		}catch(Exception e){
			log.debug("Unable to connect to db");
			e.printStackTrace();
		}
	}
	public JobDaoImpl(){
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Job> getAllOpendJobs() {
		
		
		String hql = "from Job";
		Query query = sessionFactory.openSession().createQuery(hql);
		
		return query.list();
	}

	@Override
	@Transactional
	public Job getJobDetails(Long job_id) {
		return (Job)  sessionFactory.getCurrentSession().get(Job.class,job_id) ;
	}

	@Override
	@Transactional
	public boolean updateJob(Job job) {
		try{
			sessionFactory.getCurrentSession().update(job);
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateJob(JobApplication jobApplication) {
		try{
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		}
	private Long getMaxId(){
		log.debug("->->Starting of the method getMaxId");
		
		Long maxID = 100L;
		try{
			String hql = "select max(id) from Job";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			maxID = (Long) query.uniqueResult();
			}catch(HibernateException e){
				log.debug("It seems this is first record.setting initial id is 100 :");
				maxID = 100L;
				e.printStackTrace();
		    	}
		log.debug("Max id :" + maxID);
		return maxID+1;
	}

	@Override
	@Transactional
	public boolean save(JobApplication jobApplication) {
		log.debug("->->Starting of the save job application");
		try {
				jobApplication.setId( getMaxId());
				sessionFactory.getCurrentSession().save(jobApplication);
			} catch (HibernateException e) {
				
				e.printStackTrace();
				return false;
			}
		
		return true;
		
		
		
	}

	@Override
	@Transactional
	public boolean save(Job job) {
		log.debug("->->Starting of the save Job");
		
		try {
			sessionFactory.getCurrentSession().save(job);
		} catch (HibernateException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Job> getMyAppliedJobs(String user_ID) {
		String hql = "from Application where userID = '"+ "userID'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
	}

	@Override
	@Transactional
	public JobApplication getJobApplication(String user_ID, Long job_ID) {
		log.debug("starting of the method getJobApplication");
	String hql = "from JobApplied where userID = '"+ "userID' and jobID=" + job_ID;
	log.debug("hql" + hql);
	return (JobApplication) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

	@Override
	@Transactional
	public JobApplication getJobApplication(Long id) {
		
		return (JobApplication) sessionFactory.getCurrentSession().get(JobApplication.class,id);
	}

}
