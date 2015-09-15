/* global angular: false */
(function() {
  'use strict';
  var app = angular.module('hgsys', [
        // loading bar
        'angular-loading-bar',
        'ngAnimate',

        // ng router
        'ngRoute',

        // angular strap
        'mgcrea.ngStrap',

        // elasticsearch client
        'elasticsearch',

        // moment
        'angularMoment',

        // smart table
        'smart-table',

        // charts
        'nvd3',

        // multiple select
        'isteven-multi-select',

        // tags input
        'ngTagsInput'
      ]
  );
})();

