/* global console: true */
'use strict';
var app = angular.module('hgsys');

// view1 controller
app.controller('View1Ctrl', ['$scope',
    function($scope) {
        console.log('view 1 loaded');
    }
]);