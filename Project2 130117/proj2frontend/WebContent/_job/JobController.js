'use strict';

app.
        controller(
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
        					 $location.path('/login');
        				 }
        				 console.log("->userID :" + currentUser.id+ " applying for job :" + job_ID)
        				 JobService
        				 .applyForJob(job_ID)
        				 .then(
        						function(d){
        							self.job = d;
        							alert("You have successfully applied for job.")
        							
        							
        						} )
        			 }
        			 
        			 
        			 
        		 }
        		 
        		 
        		 
        		 
        		 
        		 
        		 
        		 
        		 ]
        		
        
        
        
        
        
        
        )