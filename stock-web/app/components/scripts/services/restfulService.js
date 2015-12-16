'use strict';
var app = angular.module('hgsys');

app.factory('mysqlService', ['$http', function($http) {

    var BASE_HISTORY = 'http://localhost:8889/api/history/';

    var getWeekly = function(code) {
        return $http.get(BASE_HISTORY + 'weekly/' + code);
    };

    var getDaily = function(code) {
        return $http.get(BASE_HISTORY + 'daily/' + code);
    };

    var get60min = function(code) {
        return $http.get(BASE_HISTORY + '60min/' + code);
    };

    var get30min = function(code) {
        return $http.get(BASE_HISTORY + '30min/' + code);
    };

    var get15min = function(code) {
        return $http.get(BASE_HISTORY + '15min/' + code);
    };

    return {
        weekly: getWeekly,
        daily: getDaily,
        minutes60: get60min,
        minutes30: get30min,
        minutes15: get15min
    };

}]);