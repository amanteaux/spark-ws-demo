'use strict';

app.service('sessionService', function($rootScope) {
	
	return {
		isAuthenticated: function() {
			return $rootScope.session;
		},
		
		logout: function() {
			$rootScope.session = null;
		},
		
		login: function(userData) {
			$rootScope.session = userData;
		},
		
		current: function() {
			return $rootScope.session;
		}
		
	};
	
});