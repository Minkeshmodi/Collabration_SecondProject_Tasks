'use strict';

app.controller(
        		'JobController',
        		[
        		 'JobService',
        		 '$scope',
        		 '$location',
        		 '$rootScope',
        		 function(JobService,$scope,$location,$rootScope){
        			 console.log("JobController.....")
        			 var self = this;
        			 
        			 self.job = {
        					 
        					 id : '',
        					 title: '',
        					 description : '',
        					 dataTime : '',
        					 qualification : '',
        					 status : '',
        					 errorCode : '',
        					 errorMessage : ''
        						 };
        			 self.jobs = [];
        			 
        			 self.applyForJob = applyForJob
        			 
        			 function applyForJob(job_ID){
        				 console.log("applyForJob");
        				 var currentUser = $rootScope.currentUser
        				 console.log("currentUser.id:"  + currentUser.id)
        				 
        				 if(typeof currentUser.id == 'undefined'){
        					 alert("Please login to apply for the job")
        					 console.lo("User not logged in. Can not apply for job");
        					// $location.path('/login');
        					 return
        				 }
        				 console.log("->userID :" + currentUser.id+ " applying for job :" + job_ID)
        				 JobService
        				 .applyForJob(job_ID)
        				 .then(
        						function(d){
        							self.job = d;
        							alert("You have successfully applied for job.")
        						},
        						function(errorResponse){
        							console.error('Error while applying for job request');
        						
        							
        						});
        			
        		 }
        			 
        			self.getMyAppliedJobs = function(){
        				console.log('calling the method getMyAppliedJobs');
        				JobService.getMyAppliedJobs().then(function(d){
        					self.jobs = d;
        					},function(errorResponse){
        						console.error('Error while fetching jobs');
        					});
        				};
        				self.rejectJobApplication = function(user_ID){
        					var job_ID = $rootScope.selectJob.id;
        					JobService.rejectJobApplication(user_ID,job_ID)
        						.then(
        								
        						function(d){
        							self.job = d;
        							alert("You are successfully rejected the job application");
        					},
        					function(errorResponse){
        						console.error('Error while rejecting Job application');
        					
        					});
        				};
        				
        				self.callForInterview = function(user_ID){
        					
        					var job_ID = $rootScope.selectedJob.id;
        					JobService.callForInterview(user_ID,job_ID)
        					.then(
        						function(d){
        							self.job= d;
        							alert("Application status changed as call for interview")
        							
        						},
        						function(errorResponse){
        							
        							console.error('Error while changing the status call for interview')
        						});
        				};	
        				self.selectUser = function(user_ID){
        					var job_ID = $rootScope.selectedJob.id;
        					JobService.selectUser(user_ID,job_ID)
        					.then(
        							function(d){
        								self.job = d;
        								alert("Application status as selected")
        								
        							},
        							function(errorResponse){
        								console.error('Error while changing the status selected job')
        								
        							});
        					};	
        					
        					self.getAllJobs = function(){
        						
        						console.log('calling the method getAllJobs');
        						JobService.getAllJobs()
        						.then(
        						   function(d){
        							   self.jobs = d;
        							  },
        							  function(errorResponse){
        								  
        								  console.error('Error while fetching All opend jobs');
        								  
        								  
        							  });
        						
        					};
        					self.getAllJobs();
        					self.submit = function(){
        						{
        					
        						console.log('submit a new job',self.job);
        						self.postJob(self.job);
        					
        					}
        					self.reset();
        					};
        					self.postAJob = function(job){
        						console.log('submit a new job',self.job);
        						JobService.postAJob(job).then(function(d){
        							alert("You are successfully posted the job")
        							
        						},function(errorResponse){
        							console.error('Error while posting job');
        							
        						});
        						
        					};
        						
        					self.getJobDetails = getJobDetails
        					function getJobDetails(job_ID){
        						console.log('get Job details of the id',job_ID);
        						JobService.getJobDetails(job_ID)
        						.then(
        								function(d){
        									self.job = d;
        									$location.path('/view_job_details');
        								},function(errorResponse){
        									
        									console.error('Error while fetching blog details');
        								});
        					};
        						self.reset = function(){
        							
        							console.log('resetting the Job');
        							self.job = {
        									
        									id : '',
        									title : '',
        									description : '',
        									dateTime : '',
        									qualification : '',
        									status : '',
        									errorCode : '',
        									errorMessage : ''
        							};
        							$scope.myForm.$setPristine();
        						};
        					}
        					 ]);