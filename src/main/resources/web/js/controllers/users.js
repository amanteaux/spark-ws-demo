'use strict';

app
.controller('userTable', function ($scope, userService, sessionService, alertService) {
	var userWs = userService.user(sessionService.current()); 
	
	var updateUsers = function() {
		userWs
		.query()
		.$promise
		.then(function(users) {
			$scope.users = users;
		}, userService.defaultErrorHandling);
	};
	
	updateUsers();
	
	$scope.remove = function(user) {
		userWs
			.remove(user)
			.$promise
			.then(function() {
				alertService.append("success", "User '" + user.login + "' has successfully been deleted");
				
				updateUsers();
			}, userService.defaultErrorHandling);
	};
})
;