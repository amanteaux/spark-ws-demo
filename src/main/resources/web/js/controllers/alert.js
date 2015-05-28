'use strict';

app
.controller('alertController', function ($scope, alertService) {
	$scope.hide = function() {
		alertService.remove();
	}
})
;