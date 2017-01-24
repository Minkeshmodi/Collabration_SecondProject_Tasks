package com.niit.backend.controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.backend.dao.BulletinDao;
import com.niit.backend.model.Bulletin;


@RestController
public class BulletinController {
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	BulletinDao service;
	
	@RequestMapping(value = "/addbulletin/", method = RequestMethod.POST)
	public ResponseEntity<Bulletin> createBulletin(@RequestBody Bulletin bulletin)
	{
		log.debug("calling => createBulletin() method");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		bulletin.setPostdate(dateFormat.format(date));
		bulletin.setFlagShow('Y');

		boolean flag = service.saveBulletin(bulletin);
		
		if(!flag){
			log.debug("error in calling => createBulletin() method");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allbulletins", method = RequestMethod.GET)
	public ResponseEntity<List<Bulletin>> listAllBulletins()	{

		log.debug("calling => listAllBulletins() method");
		List<Bulletin> lsts = service.getAllBulletin('Y');
		if(lsts.isEmpty()){
			return new ResponseEntity<List<Bulletin>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Bulletin>>(lsts, HttpStatus.OK);
	}
	

}
