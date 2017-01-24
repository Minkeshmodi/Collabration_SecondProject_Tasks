package com.niit.backend.implementation;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import javax.transaction.Transactional;
import com.niit.backend.dao.FriendDao;
import com.niit.backend.model.Friend;

import oracle.net.aso.l;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository(value = "FriendDao")
@Transactional
public class FriendDaoImpl implements FriendDao {
	private static final Logger log = LoggerFactory.getLogger(FriendDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Friend friend;

	public FriendDaoImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error("unable to connect to db");
			e.printStackTrace();
		}
	}

	public FriendDaoImpl() {

	}

	private Integer getMaxId() {
		log.debug("Starting of the method getMaxId");
		String hql = "select max(id) from Friend";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID;
		try {
			maxID = (Integer) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 100;
		}
		log.debug("max id :" + maxID);
		return maxID;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Friend> getMyFriends(String userID) {
String hql = "from Friend where userID='"+userID+"'and status='A'";
		log.debug("getMyFriend hql1:" + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
	List<Friend> list1=(List<Friend>) query.list();
	return list1;
	}

	@Override
	@Transactional
	public Friend get(String userID, String friendID) {
		String hql = "from Friend where userID =" + "'" + userID + "' and friendID='" + friendID + "'";
		log.debug("hql:" + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (Friend) query.uniqueResult();
	}

	@Override
	@Transactional
	public boolean save(Friend friend) {
		try {
			log.debug("*****88Previous id" + getMaxId());
			
			log.debug("*********generated id:" + getMaxId());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public boolean update(Friend friend) {
		try{
			sessionFactory.getCurrentSession().update(friend);
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public void delete(String userID, String friendID) {
		Friend friend = new Friend();
		friend.setErrorCode(friendID);
		friend.setErrorMessage(userID);
		sessionFactory.openSession().delete(friend);

	}

	@Override
	@Transactional
	public List<Friend> getNewFriendRequest(String userID) {
		List<Friend> allFriends = null;
		try{
			log.debug("Starting of the method");
			System.out.println("Pending");
			allFriends = sessionFactory.getCurrentSession()
					.createQuery("FROM Friend where friendID ='"+userID+"'and status='N'").list();
		   System.out.println("Pending :" + allFriends.size());
		   if(allFriends == null || allFriends.isEmpty()){
			   log.debug("Record not found in Friends table");
		   }
		   }catch(HibernateException ex){
			   log.debug("fetch error :"+ex.getMessage());
			   ex.printStackTrace();
		   }
		return allFriends;
		}

	@Override
	@Transactional
	public void setOnline(String friendID) {
		log.debug("Starting of the method setOnline");
		String hql = "UPDATE Friend SET isOnline= 'Y' where friendID='" + friendID + "'";
		log.debug("hql:" + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the method setOnline");

	}

	@Override
	@Transactional
	public void setoffLine(String friendID) {
		log.debug("Starting of the method setoffLine");
		String hql = "UPDATE Friend SET isoffline= 'N' where friendID='" + friendID + "'";
		log.debug("hql:" + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the method setoffline");

	}

}

