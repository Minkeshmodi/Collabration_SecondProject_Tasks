package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Event;

public interface EventDao {
	public List<Event> getAllEvents(char status);

	public Event getEventByID(int evtid);
	
	public boolean saveEvent(Event event);
	
	public boolean removeEvent(int evtid);
	

}
