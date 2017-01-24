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

import com.niit.backend.dao.EventDao;
import com.niit.backend.model.Event;
@EnableTransactionManagement
@Repository("eventDao")
public class EventDaoImpl implements EventDao
{
	private static final Logger log = LoggerFactory.getLogger(BulletinDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public EventDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	public  EventDaoImpl(){
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Event> getAllEvents(char status) {
		List<Event> allEvent = null;
		try{
			
			log.debug("Method => getAllEvents() execution is starting");
			allEvent = sessionFactory.getCurrentSession().createQuery("FROM EventMaster where showflag = 'Y'").list();
			if(allEvent==null || allEvent.isEmpty()){
				log.debug("Record not found in UserType table");
			}
		}
		catch(HibernateException ex){
			log.debug("Fetch Error :" + ex.getMessage());
			ex.printStackTrace();
		}
		return allEvent;
	}

	@Override
	@Transactional
	public Event getEventByID(int evtid) {
		try
		{
			log.debug("Method => getEventByID() execution is starting");
			return (Event) sessionFactory.getCurrentSession().get(Event.class, evtid);
		}
		catch(HibernateException ex){
			log.debug("Data Save Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public boolean saveEvent(Event event) {
		try
		{
			log.debug("Method => saveEvent() execution is starting");
			sessionFactory.getCurrentSession().saveOrUpdate(event);
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
	public boolean removeEvent(int evtid) {
		boolean flag;
		String SQL = "Delete from EventMaster where eventid = " + evtid;
		try{
			
			int res = sessionFactory.openStatelessSession().createQuery(SQL).executeUpdate();
			flag =  res == 1 ? true : false;
		}
		catch(HibernateException ex){
			flag=false;
		}
		return flag;
	}
	

}
