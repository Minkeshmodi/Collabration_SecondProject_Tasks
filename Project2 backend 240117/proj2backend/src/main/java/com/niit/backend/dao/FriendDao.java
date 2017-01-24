package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Friend;

public interface FriendDao {
	
public List<Friend> getMyFriends(String userID);
	
	public Friend get(String userID,String friendID);
	
	public boolean save(Friend friend);
	
	public boolean update(Friend friend);
	
	public void delete(String userID,String friendID);
	
	public List<Friend> getNewFriendRequest(String friendID);
	
     public void setOnline(String friendID);
      
     public void setoffLine(String friendID);
    
}