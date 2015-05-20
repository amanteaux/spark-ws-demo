'use strict';

app.service('userService', function ($resource) {
	
	var requestWithHeader = function(method, login, password) {
		return {
			'method': method,
			'headers': {
				'X-User-Login' : login,
				'X-User-Password' : password
			}
		}
	};
	
	this.user = function(login, password) {
		return $resource(
				'/user/:login',
				{login:'@login'},
				{
					get: requestWithHeader('GET', login, password),
					add: requestWithHeader('POST', login, password),
					update: requestWithHeader('POST', login, password),
					query: angular.extend({isArray: true}, requestWithHeader('GET', login, password))
				}
		);
	};
	
});