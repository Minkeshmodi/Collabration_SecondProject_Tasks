package com.niit.backend.controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.backend.dao.BlogDao;
import com.niit.backend.model.Blog;



@RestController
public class BlogController {
	@Autowired
	BlogDao service;
	
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@RequestMapping(value = "/adduserblog", method = RequestMethod.POST)
	public ResponseEntity<Blog> createUserBlog(@RequestBody Blog ublog, HttpSession session)
	{
		log.debug("calling => createUserBlog() method");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		ublog.setBlogcreatedate(dateFormat.format(date));
		ublog.setLikes(0);
		ublog.setApprove('N');
		ublog.setUseremail((String)session.getAttribute("loggeduser"));
		
		boolean flag = service.saveUserBlog(ublog);
		
		if(!flag){
			log.debug("error in calling => createUserType() method");
			return new ResponseEntity<Blog>(ublog, HttpStatus.CONFLICT);
		}
		else
		{
			log.debug("Update user blog");
			return new ResponseEntity<Blog>(ublog, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/alluserblog", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> listAllUserBlog()	{

		log.debug("calling => listAllUserType() method");
		List<Blog> lsts = service.getAllBlogs();
		if(lsts.isEmpty()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(lsts, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getblogbyid/{blgid}", method = RequestMethod.GET)
	public ResponseEntity<Blog> getblogbyid(@PathVariable("blgid") int blgid)	{

		log.debug("calling => getblogbyid() method");
	Blog userblog = service.getBlogByID(blgid);
		
		if(userblog==null){
			return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
	}

	@RequestMapping(value = "/getapproveblog/{blgid}", method = RequestMethod.POST)
	public ResponseEntity<Blog> getapproveblog(@PathVariable("blgid") int blgid)	{

		log.debug("calling => getapproveblog() method");
		boolean flag = service.updateApprove(blgid, 'Y');
		if(!flag){
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		Blog userblog = service.getBlogByID(blgid);
		return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
	}	

	@RequestMapping(value = "/getdeleteblog/{blgid}", method = RequestMethod.POST)
	public ResponseEntity<Blog> getdeleteblog(@PathVariable("blgid") int blgid)	{

		log.debug("calling => getapprovegetdeleteblogblog() method");
		boolean flag = service.getDelete(blgid);
		if(!flag){
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		Blog userblog = service.getBlogByID(blgid);
		return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
	}	

	@RequestMapping(value = "/getupdatelike/{blgid}", method = RequestMethod.POST)
	public ResponseEntity<Blog> getupdatelike(@PathVariable("blgid") int blgid)	{

		log.debug("calling => getapproveblog() method");
		boolean flag = service.getUpdateLike(blgid);
		if(!flag){
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		Blog userblog = service.getBlogByID(blgid);
		return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
	}	
	

}
