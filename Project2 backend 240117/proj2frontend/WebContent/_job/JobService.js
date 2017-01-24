'use strict';

app.factory('JobService',['$http','$q','$rootScope',function($http,$q,$rootScope){
	
	console.log("JobService...")
	var BASE_URL = 'http://localhost:8082/proj2backend'
		return{
		       applyForJob : function(job_ID){
		    	   return $http.post(BASE_URL+"/applyForJob/"+job_ID)
		    	   .then(
		    			 function(response){
		    				 return response.data;
		    				 },
		    				 function(errorresponse){
		    					 
		    					 console.error('Error while applying for job');
		    					 return $q.reject(errorresponse);
		    				 }
		   );
		    	    },
		    	    getJobDetails : function(job_ID){
		    	    	console.log("Getting job details of " + job_ID)
		    	    	return  $http.post(BASE_URL+"/getJobDetails/"+job_ID)
		    	    	.then(
		    	    	function(response){
		    	    		$rootScope.selectedJob = response.data;
		    	    		return response.data;
		    	    	},function(errorResponse){
		    	    		console.error('Error while getting job details');
		    	    		return $q.reject(errorResponse);
		    	    	}		
		    	    	
		    	    	);
		    	    	},
		    	    	getMyAppliedJobs: function(){
		    	    		return  $http.post(BASE_URL+'/getMyAppliedJobs')	
		    	    		.then(
		    	    				function(response){
		    	    					return response.data;
		    	    				},function(errorResponse){
		    	    					console.error('Error while getting applyied jobs');
		    	    					return $q.reject(errorResponse);
		    	    					
		    	    				});
		    	    	},
		                postAJob: function(job){
		                	return $http.post(BASE_URL+'/postAJob',job)
		                	.then(
		                			function(response){
		                				return response.data;
		                				
		                			},function(errorResponse){
		                				console.error('Error while posting job');
		                				return $q.reject(errorResponse);
		                			});
		                },
		                rejectJobApplication:function(user_ID,job_ID){
		                	return $http.put(BASE_URL+'/rejectJobApplication'+user_ID+"/"+job_ID)
		                	.then(
		                	     function(response){
		                	    	 return response.data;
		                	     },
		                	     function(errorResponse){
		                	    	 console.error('Error while rejecting job');
		                	    	 return $q.reject(errorResponse);
		                	     });
		                },
		                callForInterview: function(id){
		                	return $http.put(BASE_URL+'/callForInterview'+user_ID,job_ID)
		                	.then(
		                	     function(response){
		                	    	 return response.data;
		                	     },
		                	     function(errorResponse){
		                	    	 console.error('Error while call for interview');
		                	    	 return $q.reject(errorResponse);
		                	     });
		                	
		                	
		                	
		                },
		                selectUser: function(id){
		                	return $http.put(BASE_URL+'/selectUser'+user_ID,job_ID)
		                	.then(
		                			 function(response){
			                	    	 return response.data;
			                	     },
			                	     function(errorResponse){
			                	    	 console.error('Error while selecting the user for job');
			                	    	 return $q.reject(errorResponse);
			                	     });
			           },
			           getAllJobs: function(){
			        	   return $http.get(BASE_URL+'/getAllJobs')
			        		.then(
		                			 function(response){
			                	    	 return response.data;
			                	     },
			                	     function(errorResponse){
			                	    	 console.error('Error while getting all jobs');
			                	    	 return $q.reject(errorResponse);
			                	     });
			           }
			           };
			           
	}]);
			          
			           

	
	
	



