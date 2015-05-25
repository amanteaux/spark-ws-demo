'use strict';

app.service('sessionService', function($rootScope, $location) {
	
	this.isAuthenticated = function() {
		return $rootScope.session;
	};
	
	var logout = function() {
		$rootScope.session = null;
		
		$location.path('/');
	};
	
	this.logout = logout;
	
	this.login = function(userData) {
		userData.logout = logout;
		
		$rootScope.session = userData;
		
		$location.path('/user/');
	};
	
});