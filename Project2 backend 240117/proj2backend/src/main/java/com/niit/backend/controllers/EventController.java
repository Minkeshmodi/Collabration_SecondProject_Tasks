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

import com.niit.backend.dao.EventDao;
import com.niit.backend.model.Event;
@RestController
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	EventDao service;
	
	@RequestMapping(value = "/addevent/", method = RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event)
	{
		log.debug("calling => createEvent() method");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		event.setEventdt(dateFormat.format(date));
		event.setShowFlag('Y');

		boolean flag = service.saveEvent(event);
		
		if(!flag){
			log.debug("error in calling => createEvent() method");
			return new ResponseEntity<Event>(event, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allevents", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllEvents()	{

		log.debug("calling => listAllEvents() method");
		List<Event> lsts = service.getAllEvents('Y');
		if(lsts.isEmpty()){
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Event>>(lsts, HttpStatus.OK);
	}
}


