'use strict';

app.service('serverStatus', function ($rootScope, $resource) {
	
	this.watch = function() {
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
	}
	
});