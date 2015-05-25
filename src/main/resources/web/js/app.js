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
.run(function($rootScope, $location, sessionService) {
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		if (!sessionService.isAuthenticated() && next.originalPath != "/") {
			$location.path('/');
		}         
	});
})
.run(function(serverStatus) {
	serverStatus.watch();
})
;