package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Blog;

public interface BlogDao {
public List<Blog> getAllBlogs();
	
	public boolean saveUserBlog(Blog ubObj);
	
	public boolean updateApprove(int blgid, char flag);
		
	public Blog getBlogByID(int blgid);

	public boolean getUpdateLike(int blgid);
	
	public boolean getDelete(int blgid);

}
