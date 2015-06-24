'use strict';

var app = angular
.module('sparkWs', [
	'ngRoute',
	'ngResource'
])
.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            controller: 'login',
            templateUrl: 'views/login.html'
        })
        .when('/user/', {
            controller: 'users',
            templateUrl: 'views/users.html'
        })
        .otherwise({
            redirectTo: '/'
        });
})
.run(function($rootScope, $location, sessionService, alertService) {
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		if (!sessionService.isAuthenticated()) {
			if(next.originalPath != "/") {
				$location.path('/');
			}
		}
		// whenever the route changes, alerts should be dismissed (except on the login page when the user is disconnected)
		else {
			alertService.remove();
		}         
	});
	
	$rootScope.$on("UNAUTHORIZED", function() {
		sessionService.logout();
		$location.path('/');
	});
})
.run(function(serverStatusService) {
	serverStatusService.watch();
})
;