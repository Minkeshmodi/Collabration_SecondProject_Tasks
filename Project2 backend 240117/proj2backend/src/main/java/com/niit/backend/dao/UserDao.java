package com.niit.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.backend.model.User;

@Repository("userDao")
public interface UserDao {
public boolean save(User user);
	
	public boolean update(User user);
	
	
	public void deleteuserid(String id);
	
	public User get(String id);
	
	public User authenticate(String id, String password);

	
	public List<User> list();

}
