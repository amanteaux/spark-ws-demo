'use strict';

app.service('userService', function ($rootScope, $resource, alertService) {
	
	var requestWithHeader = function(method, login, password) {
		return {
			'method': method,
			'headers': {
				'X-User-Login' : login,
				'X-User-Password' : password
			}
		}
	};
	
	return {
		user: function(user) {
			return $resource(
					'/user/:login',
					{login:'@login'},
					{
						get: requestWithHeader('GET', user.login, user.password),
						add: requestWithHeader('POST', user.login, user.password),
						update: requestWithHeader('PUT', user.login, user.password),
						query: angular.extend({isArray: true}, requestWithHeader('GET', user.login, user.password)),
						remove: requestWithHeader('DELETE', user.login, user.password)
					}
			);
		},
		
		defaultErrorHandling: function(reason) {
			if(reason.status == 401) {
				alertService.append("danger", "Wrong credentials");
				$rootScope.$broadcast('UNAUTHORIZED');
			} else {
				alertService.internalError();
			}
		}
	};
	
});