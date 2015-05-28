'use strict';

app
.controller('login', function ($scope, $location, userService, sessionService) {
	if(sessionService.isAuthenticated()) {
		$location.path('/user/');
	}
	
	$scope.login = function(user) {
		userService
		.user(user)
		.get({login: user.login})
		.$promise
		.then(function(userData) {
			sessionService.login(userData);
			
			$location.path('/user/');
		}, userService.defaultErrorHandling);
	};
})
.controller('logout', function ($scope, $location, sessionService) {
	$scope.logout = function() {
		sessionService.logout();
		$location.path('/');
	}
})
;