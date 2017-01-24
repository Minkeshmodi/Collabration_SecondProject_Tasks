package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Bulletin;

public interface BulletinDao {

	public List<Bulletin> getAllBulletin(char showflag);

	public Bulletin getBulletinByID (int v_bulid);
	
	public boolean saveBulletin(Bulletin bulletin);

}
