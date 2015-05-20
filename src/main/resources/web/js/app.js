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
.run(function(serverStatus) {
	serverStatus.watch();
})
;