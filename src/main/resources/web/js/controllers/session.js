'use strict';

app
.controller('login', function ($scope, $location, userService, sessionService) {
	if(sessionService.isAuthenticated()) {
		$location.path('/user/');
	}
	
	$scope.login = function(user) {
		userService
		.user(user.login, user.password)
		.get({login: user.login})
		.$promise
		.then(sessionService.login, function(reason) {
			if(reason.data.errorCode) {
				// TODO 401 => error
			} else {
				// TODO generic error
			}
		});
	};
})
.controller('logout', function ($scope) {
	
	
})
;