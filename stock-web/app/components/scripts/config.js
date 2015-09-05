'use strict';
var app = angular.module('hgsys');

app.config(['$routeProvider', function($routeProvider) {
    // other cases
    $routeProvider.otherwise({ redirectTo: '/view1' });

    $routeProvider.when('/view1', {
        templateUrl: 'partials/view1/view1.html',
        controller: 'View1Ctrl'
    });

    $routeProvider.when('/view2', {
        templateUrl: 'partials/view2/view2.html',
        controller: 'View2Ctrl'
    });

    $routeProvider.when('/view3', {
        templateUrl: 'partials/view3/view3.html',
        controller: 'View3Ctrl'
    });
}]);
