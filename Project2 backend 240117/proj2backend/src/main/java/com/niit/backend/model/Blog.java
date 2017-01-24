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
@Table(name = "Blog")
@Component
public final class Blog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int blogid;
	
	@Column
	@NotEmpty(message="Blog title should not be blank")
	private String blogtitle;
	
	@Column
	@NotEmpty(message="Blog contents should not be blank")
	private String blogcontent;
		
	@Column
	private String blogcreatedate;
	
	@Column
	private char approve;

	@Column
	private String useremail;

	@Column
	private int likes;
	
	public int getBlogid() {
		return blogid;
	}

	public void setBlogid(int blogid) {
		this.blogid = blogid;
	}

	public String getBlogtitle() {
		return blogtitle;
	}

	public void setBlogtitle(String blogtitle) {
		this.blogtitle = blogtitle;
	}

	public String getBlogcontent() {
		return blogcontent;
	}

	public void setBlogcontent(String blogcontent) {
		this.blogcontent = blogcontent;
	}

	public String getBlogcreatedate() {
		return blogcreatedate;
	}

	public void setBlogcreatedate(String blogcreatedate) {
		this.blogcreatedate = blogcreatedate;
	}

	public char getApprove() {
		return approve;
	}

	public void setApprove(char approve) {
		this.approve = approve;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

}
