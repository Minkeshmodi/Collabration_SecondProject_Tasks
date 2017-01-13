package com.niit.backend.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.backend.dao.JobDao;
import com.niit.backend.model.Job;
import com.niit.backend.model.JobApplication;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
@RestController
public class JobController {
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	Job job;
	
	@Autowired
	JobApplication jobApplication;

	@Autowired
	JobDao jobDao;
	
	@Autowired
	 HttpSession httpSession;

	@RequestMapping(value = "/getAllJobs/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllOpendJobs() {
		logger.debug("starting of the method getAllOpendJobs");
		List<Job> jobs = jobDao.getAllOpendJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyAppliedJobs/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getMyAppliedJobs(HttpSession httpSession) {
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Job> jobs = new ArrayList<Job>();
		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			job.setErrorCode("404");
			job.setErrorMessage("You have to login to see you applied jobs");
			jobs.add(job);
		} else {
			jobs = jobDao.getMyAppliedJobs(loggedInUserID);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getJobDetails/{job_ID}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("job_ID") Long job_ID) {
		logger.debug("Starting of the method getJobDetails");
		Job job = jobDao.getJobDetails(job_ID);
		if (job == null) {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Job not available for this id :" + job_ID);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		logger.debug("Starting of the method postAJob");
		job.setStatus('V');
		if (jobDao.save(job) == false) {
			job.setErrorCode("404");
			job.setErrorMessage("Not able to post a job");
			logger.debug("Not able to post a job");
		} else {
			job.setErrorCode("200");
			job.setErrorMessage("Successfully posted the job");
			logger.debug("Successfully poted the job");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{job_ID}", method = RequestMethod.POST)
	public ResponseEntity<JobApplication> applyForJob(@PathVariable("job_ID") Long job_ID) {
		logger.debug("Starting of the method applyForJob");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You are loggin to apply for a job");
		} else {
			if (isUserAppliedForTheJob(loggedInUserID, job_ID) == false) {
				jobApplication.setJob_id(job_ID);
				jobApplication.setUser_id(loggedInUserID);
				jobApplication.setStatus('N');
				jobApplication.setDate_applied(new Date(System.currentTimeMillis()));
				logger.debug("Applied Date :" + jobApplication.getDate_applied());
				if (jobDao.save(jobApplication)) {
					jobApplication.setErrorCode("200");
					jobApplication.setErrorMessage("You are successfully applied for the job :" + job_ID);
					logger.debug("apply for the job");
				}
			} else {
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("You already applied for the job " + job_ID);
				logger.debug("Not able to apply for the job");
			}
		}
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/selectUser/{user_ID}/{job_ID}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> selectUser(@PathVariable("user_ID") String user_ID,
			@PathVariable("job_ID") Long job_ID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method selectUser");
		jobApplication = updateJobApplicationStatus(user_ID, job_ID, 'S', remarks);
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/callForInterview/{user_ID}/{job_ID}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> callForInterview(@PathVariable("user_ID") String user_ID,
			@PathVariable("job_ID") Long job_ID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method can callForInterview");
		jobApplication = updateJobApplicationStatus(user_ID, job_ID, 'C', remarks);
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectJobApplication/{user_ID}/{job_ID}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> rejectJobApplication(@PathVariable("user_ID") String user_ID,
			@PathVariable("job_ID") Long job_ID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method rejectJobApplication");
		jobApplication = updateJobApplicationStatus(user_ID, job_ID, 'R', remarks);
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	private JobApplication updateJobApplicationStatus(String user_ID, Long job_ID, char status, String remarks) {
		logger.debug("Starting of the method updateJobApplicationStatus");
		if (isUserAppliedForTheJob(user_ID, job_ID) == false) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage(user_ID + "not applied for the job" + job_ID);
			return jobApplication;

		}
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		logger.debug("loggedInUserRole:" + loggedInUserRole);
		if (loggedInUserRole == null || loggedInUserRole.isEmpty()) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You are not logged in");
			return jobApplication;
		}
		if (!loggedInUserRole.equalsIgnoreCase("admin")) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You are not admin.You can not do this" + "Operation");
			return jobApplication;
		}
		jobApplication = jobDao.getJobApplication(user_ID, job_ID);
		jobApplication.setStatus(status);
		jobApplication.setRemarks(remarks);
		if (jobDao.updateJob(jobApplication)) {
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("Successfully updated the status as" + status);
			logger.debug("Successfully updated the status as" + status);
		} else {
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("update the status");
			logger.debug("update the status");

		}
		return jobApplication;
	}

	private boolean isUserAppliedForTheJob(String user_ID, Long job_ID) {
		if (jobDao.getJobApplication(user_ID, job_ID) == null) {
			return false;
		}
		return true;
	}

}
