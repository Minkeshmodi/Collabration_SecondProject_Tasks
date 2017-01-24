package com.niit.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "FriendDetail")
@Component
public class Friend extends BaseDomain    {
	
	private String Userid; 
	
	
	    @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private int id;
	    
	
		
		private String Friendid;
		
		private String status;
		
		private char isonline;

		public String getUserid() {
			return Userid;
		}

		public void setUserid(String userid) {
			Userid = userid;
		}

		public String getFriendid() {
			return Friendid;
		}

		public void setFriendid(String friendid) {
			Friendid = friendid;
		}

		public int getId() {
			return id;
		}
       public void setId(int id) {
			this.id = id;
		}
	public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public char getIsonline() {
			return isonline;
		}

		public void setIsonline(char isonline) {
			this.isonline = isonline;
		}
}
