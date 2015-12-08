'use strict';
var app = angular.module('hgsys');

app.factory('mysqlService', ['$http', function($http) {

	var BASE_HISTORY = 'http://localhost:8888/api/history/';

	var getDaily = function(code) {
		return $http.get(BASE_HISTORY + code);
	};

	return {
		daily: getDaily
	};

}]);