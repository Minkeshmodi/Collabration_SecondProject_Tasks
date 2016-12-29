package com.niit.collaboration.DAO;

import java.util.List;

import com.niit.collaboration.model.User;

public interface UserDAO {
	
	public List<User>  list();
	
	public boolean save(User user);
	public boolean update(User user);
	public User  get(String id);
	public User validate(String id,String password);
	
	

}
