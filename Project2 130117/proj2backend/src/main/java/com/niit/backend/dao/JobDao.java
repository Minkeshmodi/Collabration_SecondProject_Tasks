package com.niit.backend.dao;
import java.util.List;



import com.niit.backend.model.Job;
import com.niit.backend.model.JobApplication;



public interface JobDao {
	
	public List<Job> getAllOpendJobs();
	
	public Job getJobDetails(Long job_id);
	
	public boolean updateJob(Job job);
	
	public boolean updateJob(JobApplication jobApplication);
	
	public boolean save(JobApplication jobApplication);
	
	public boolean save(Job job);
	
	public List<Job> getMyAppliedJobs(String user_ID);
	
	public JobApplication getJobApplication(String user_ID,Long job_ID);
	
	public JobApplication getJobApplication(Long id);

}
