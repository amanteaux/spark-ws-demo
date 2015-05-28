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
            controller: 'userTable',
            templateUrl: 'views/users.html'
        })
})
.run(function($rootScope, $location, sessionService, alertService) {
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		if (!sessionService.isAuthenticated() && next.originalPath != "/") {
			$location.path('/');
		}
		// if an error happened, it often should to be displayed on the login page
		// in other cases, whenever the route changes, alerts should be dismissed
		else {
			alertService.remove();
		}         
	});
})
.run(function(serverStatusService) {
	serverStatusService.watch();
})
;