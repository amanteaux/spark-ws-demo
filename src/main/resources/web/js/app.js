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
})
.run(function(serverStatus) {
	serverStatus.watch();
})
;