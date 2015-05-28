'use strict';

app.service('alertService', function($rootScope) {
	
	return {
		remove: function() {
			$rootScope.alert = null;
		},
		
		/**
		 * type in : success, info, warning, danger
		 */
		append: function(type, message) {
			$rootScope.alert = {
				"type": type,
				"message": message
			};
		},
		
		internalError: function() {
			this.append("danger", "Connexion error");
		}
	}
	
});