'use strict';

app.service('userService', function ($resource) {
	
	this.user = function(login, password) {
		return $resource(
				'/user/:login',
				{login:'@login'}
				// TODO headers + methods
		);
	}
	
});