'use strict';

app
.controller('login', function ($scope, $location, userService) {
	$scope.login = function(user) {
		userService
		.user(user.login, user.password)
		.get({login: user.login})
		.$promise
		.then(function() {
			$location.path('/user/');
		}, function(reason) {
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