package com.niit.backend.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "Event")
@Component
public class Event {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	int eventid;

	@Column
	@NotEmpty(message="Event subject should not be blank")
	String subject;

	@Column
	@NotEmpty(message="Event date should not be blank")
	String eventdt;
	
	@Column
	@NotEmpty(message="Event description should not be blank")
	@Size(max=1000, message="Description should not be exceeds 1000 characters")
	String description;

	@Column
	@NotEmpty(message="Event subject should not be blank")
	String venue;
	
	@Column
	char showFlag;

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEventdt() {
		return eventdt;
	}

	public void setEventdt(String eventdt) {
		this.eventdt = eventdt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public char getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(char showFlag) {
		this.showFlag = showFlag;
	}
}
