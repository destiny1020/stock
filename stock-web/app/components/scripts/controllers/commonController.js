/* global console: true */
'use strict';
var app = angular.module('hgsys');

// common controller
app.controller('commonController', ['$scope', '$log', '$timeout', function($scope, $log, $timeout) {
    console.log('start');
}]);