package com.niit.backend.implementation;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.backend.dao.BulletinDao;
import com.niit.backend.model.Bulletin;


@EnableTransactionManagement
@Repository("bulletinDao")
public class BulletinDaoImpl implements BulletinDao {
	private static final Logger log = LoggerFactory.getLogger(BulletinDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public BulletinDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	public BulletinDaoImpl(){
		
	}

	@SuppressWarnings("unchecked")
    @Transactional
	public List<Bulletin> getAllBulletin(char showflag) {
		List<Bulletin> allBulletin = null;
		try{
			log.debug("Method => getAllJobs() execution is starting");
			allBulletin = sessionFactory.getCurrentSession().createQuery("FROM Bulletin where flagshow='Y'").list();
			if(allBulletin==null || allBulletin.isEmpty()){
				log.debug("Record not found in Career table");
			}
		}
		catch(HibernateException ex){
			log.debug("Fetch Error :" + ex.getMessage());
			ex.printStackTrace();
		}
		return allBulletin;
	}


	@Transactional
	public Bulletin getBulletinByID(int v_bulid) {
		return (Bulletin) sessionFactory.getCurrentSession().get(Bulletin.class, v_bulid);
	}

	@Transactional
	public boolean saveBulletin(Bulletin bulletin) {
		try
		{
			log.debug("Method => saveBulletin() execution is starting");
			sessionFactory.getCurrentSession().saveOrUpdate(bulletin);
			return true;
		}
		catch(HibernateException ex){
			log.debug("Data Save Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}
	
	

}
