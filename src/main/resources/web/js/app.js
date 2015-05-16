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
.run(function($rootScope, $resource) {
            $rootScope.server = {};
            var serverWs = $resource('/status/');
            var serverUp = function() {
                $rootScope.server.statusClass = 'label-success';
                $rootScope.server.statusLabel = 'Up';
            };
            var serverDown = function() {
                $rootScope.server.statusClass = 'label-danger';
                $rootScope.server.statusLabel = 'Down';
            };
            var updateStatus = function() {
              serverWs
                  .get()
                  .$promise
                  .then(function(data) {
                      if(data && data.alive) {
                          serverUp();
                      } else {
                          serverDown();
                      }
                  }, serverDown)
                  .finally(function() {
                      setTimeout(updateStatus, 1000);
                  });
            };
            updateStatus();
})
;