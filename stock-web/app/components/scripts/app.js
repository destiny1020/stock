/* global angular: false */
(function() {
  'use strict';
  var app = angular.module('hgsys', [
        // loading bar
        'angular-loading-bar',
        'ngAnimate',

        // ng router
        'ngRoute',

        // angular ui
        'ui.bootstrap',

        // elasticsearch client
        'elasticsearch',

        // moment
        'angularMoment',

        // smart table
        'smart-table',

        // charts
        'nvd3'
      ]
      // issue: http://stackoverflow.com/questions/16569841/angularjs-html5-mode-reloading-the-page-gives-wrong-get-request
      //     ,
      //     ['$locationProvider', function($locationProvider){
      //       $locationProvider.html5Mode(true);
      //     }
      // ]
  );

  // app.run(['hrData', function(hrData) {
  //     // trigger the division fetching
  //     hrData.divisions();
  // }]);

  // define constants
  //app.constant('baseUrl', 'http://localhost:8081/hbp/');
  //app.constant('esBaseUrl', 'http://localhost:9200/');
  //app.constant('fsBaseUrl', 'http://localhost/z/');
})();

