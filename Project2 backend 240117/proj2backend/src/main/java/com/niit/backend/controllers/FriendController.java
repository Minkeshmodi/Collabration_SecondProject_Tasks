package com.niit.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.niit.backend.dao.FriendDao;
import com.niit.backend.model.Friend;
import com.niit.backend.dao.UserDao;

@RestController
public class FriendController {

	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	FriendDao friendDao;

	@Autowired
	Friend friend;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	HttpSession session;

	

	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends(){
		logger.debug("calling of the method getmyFriends");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		List<Friend> myFriends = new ArrayList<Friend>();
		if(loggedInUserID == null){
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK); 
		}
		logger.debug("getting friends of:"+loggedInUserID);
		myFriends = friendDao.getMyFriends(loggedInUserID);
		if(myFriends.isEmpty()){
			logger.debug("Friends does not exist for the user:"+loggedInUserID);
			friend.setErrorCode("404");
			friend.setErrorMessage("You are not have any friends");
		}
		logger.debug("send the friend list");
		return  new  ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK); 
	}
	@RequestMapping(value = "/addFriend/{friendID}", method = RequestMethod.GET)
	public  ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendID") String friendID){
		logger.debug("calling of the method sendFriendRequest");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		friend.setUserid(loggedInUserID);
		friend.setFriendid(friendID);
		friend.setStatus("N");
		friend.setIsonline('N');
		if(isUserExist(friendID)==false){
			friend.setErrorCode("404");
			friend.setErrorMessage("No User exist with the id:"+friendID);
			}
		if(friendDao.get(loggedInUserID, friendID)!=null){
			friend.setErrorCode("404");
			friend.setErrorMessage("You are already sent the friend request");
			}else{
				friendDao.save(friend);
			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request successfull.."+friendID);
			}
		return new ResponseEntity<Friend>(friend,HttpStatus.OK); 
}
	private boolean isFriendRequestAvailable(String friendID){
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		if(userDao.get(friendID)==null){
			return false;
		}else
			return true;
		}
	@RequestMapping(value = "/unFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendID") String friendID){
		logger.debug("calling of the method unfriend");
	updateRequest(friendID,"U");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
		}
	@RequestMapping(value = "/reject/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendID") String friendID){
		logger.debug("calling of the method rejectFriendRequest");
			updateRequest(friendID,"R");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
		}
	@RequestMapping(value = "/acceptFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendID") String friendID){
		logger.debug("calling of the method acceptFriendRequest");
		updateRequest(friendID,"A");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	private Friend updateRequest(String friendID,String status){
		logger.debug("Starting of the method updateRequest");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		logger.debug("loggedInUserID:"+loggedInUserID);
		if(isFriendRequestAvailable(friendID)==false){
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist.so you can not update to" + status);
		}
		if(status.equals("A")|| status.equals("R"))
			friend = friendDao.get(friendID, loggedInUserID);
		else
			friend = friendDao.get(loggedInUserID, friendID);
		friend.setStatus(status);
		friendDao.update(friend);
		friend.setErrorCode("200");
		friend.setErrorMessage("Request from " + friend.getUserid()+"To"+friend
				.getFriendid()+"has updated to:"+status);
		logger.debug("Ending of the method updateRequest");
		return friend;
	}
	@RequestMapping(value="/getMyFriendRequest/",method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequest(){
		logger.debug("calling of the method getmyfriendrequest");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		List<Friend> myFriendRequests = friendDao.getNewFriendRequest(loggedInUserID);
		return new ResponseEntity<List<Friend>>(myFriendRequests,HttpStatus.OK);
	}
     private boolean isUserExist(String id){
    	 if(userDao.get(id)==null){
    		 return true;
    	 }
    	 else
    		 return false;
     }
}
