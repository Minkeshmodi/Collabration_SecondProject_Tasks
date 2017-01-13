//var app = angular.module('myApp', [ 'ngRoute','ngCookies','ngFileUpload' ]);
var app = angular.module('myApp', [ 'ngRoute','ngCookies']);
app.config(function($routeProvider) {
	$routeProvider

	.when('/', {
		templateUrl : '_home/home.html'
		
	})

	.when('/manageUser', {
		templateUrl : '_admin/manage_users.html',
		controller : 'AdminController'
	})

	.when('/event', {
		templateUrl : '_upload/upload.html',
		controller : 'FileUploadController'
	})

	.when('/about', {
		templateUrl : '_about/about.html',
		controller : 'AboutController'
	})

	.when('/login', {
		templateUrl : '_user/login.html',
		controller : 'UserController'
	})

	.when('/register', {
		templateUrl : '_user/register.html',
		controller : 'UserController'
	})
	
	.when('/myProfile', {
		templateUrl : '_user/my_profile.html',
		controller : 'UserController'
	})
	
	.when('/manage_users', {
		templateUrl : '_admin/manage_users.html',
		controller : 'UserController'
	})


	/**
	 * Blog related mapping
	 */

	.when('/create_blog', {
		templateUrl : '_blog/create_blog.html',
		controller : 'BlogController'
	})

	.when('/list_blog', {
		templateUrl : '_blog/list_blog.html',
		controller : 'BlogController'
	})

	.when('/view_blog', {
		templateUrl : '_blog/view_blog.html',
		controller : 'BlogController'
	})

	/**
	 * Friend related mapping
	 */

	.when('/add_friend', {
		templateUrl : '_friend/add_friend.html',
		controller : 'FriendController'
	})

	.when('/search_friend', {
		templateUrl : '_friend/search_friend.html',
		controller : 'FriendController'
	})

	.when('/view_friend', {
		templateUrl : '_friend/view_friend.html',
		controller : 'FriendController'
	})
	
	.when('/viewFriendRequest', {
		templateUrl : '_friend/view_friend_request.html',
		controller : 'FriendController'
	})
	
	.when('/chat', {
		templateUrl : '_chat/chat.html',
		controller : 'ChatController'
	})
	
	.when('/chat_forum', {
		templateUrl : '_chat_forum/chat_forum.html',
		controller : 'ChatForumController'
	})



	/**
	 * Job related mappings
	 */
	.when('/job', {
		templateUrl : '_job/job.html',
		controller : 'JobController'
	})

	.when('/search_job', {
		templateUrl : '_job/search_job.html',
		controller : 'JobController'
	})

	.when('/view_applied_jobs', {
		templateUrl : '_job/view_applied_jobs.html',
		controller : 'JobController'
	})
	
	.when('/view_job_details', {
		templateUrl : '_job/view_job_details.html',
		controller : 'JobController'
	})

	.when('/post_job', {
		templateUrl : '_job/post_job.html',
		controller : 'JobController'
	})

	.otherwise({
		redirectTo : '/'
	});
});

app.run( function ($rootScope, $location,$cookieStore, $http) {

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	        var restrictedPage = $.inArray($location.path(), ['','/','/search_job','/view_blog','/login', '/register','/list_blog']) === -1;
		 console.log("Navigating to page :" + $location.path())
	        console.log("restrictedPage:" +restrictedPage)
	        console.log("currentUser:" +$rootScope.currentUser)
	        var loggedIn = $rootScope.currentUser.id;
	        
	        console.log("loggedIn:" +loggedIn)
	        
	        if(!loggedIn)
	        	{
	        	
	        	 if (restrictedPage) {
		        	  console.log("Navigating to login page:")
		        	

						            $location.path('/login');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 var userRestrictedPage = $.inArray($location.path(), ["/post_job"]) == 0;
				 
				 if(userRestrictedPage && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
     $rootScope.currentUser = $cookieStore.get('currentUser') || {};
     if ($rootScope.currentUser) {
         $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.currentUser; 
     }

});


 
    
    
