'use strict';

app
.controller('login', function ($scope, userService) {
	$scope.login = function(logins) {
		userService
		.user
		.save(user)
		.$promise
		.then(function() {
			// TODO redirect
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