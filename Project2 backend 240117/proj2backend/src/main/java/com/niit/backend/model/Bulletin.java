package com.niit.backend.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "Bulletin")
@Component
public class Bulletin {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int bulid;
	
	@Column
	private String postdate;
	
	@Column
	@NotEmpty(message="Subject should not be blank")
	private String bulsubject;

	@Column
	@NotEmpty(message="Description type should not be blank")
	private String buldescription;
	
	@Column
	private char flagShow;

	public int getBulid() {
		return bulid;
	}

	public void setBulid(int bulid) {
		this.bulid = bulid;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getBulsubject() {
		return bulsubject;
	}

	public void setBulsubject(String bulsubject) {
		this.bulsubject = bulsubject;
	}

	public String getBuldescription() {
		return buldescription;
	}

	public void setBuldescription(String buldescription) {
		this.buldescription = buldescription;
	}

	public char getFlagShow() {
		return flagShow;
	}

	public void setFlagShow(char flagShow) {
		this.flagShow = flagShow;
	}
	

}
