'use strict';

app.service('sessionService', function($rootScope) {
	
	this.isAuthenticated = function() {
		return $rootScope.session;
	};
	
	this.logout = function() {
		$rootScope.session = null;
	};
	
	this.login = function(userData) {
		$rootScope.session = userData;
	};
	
});