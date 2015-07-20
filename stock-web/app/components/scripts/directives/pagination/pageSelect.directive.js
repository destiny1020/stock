/**
 * Created by Administrator on 2015/7/20.
 */
angular.module('hgsys')
    .directive('pageSelect', function() {
        'use strict';
        return {
            restrict: 'E',
            template: '<input type="text" class="select-page" ng-model="inputPage" ng-change="selectPage(inputPage)">',
            link: function(scope, element, attrs) {
                scope.$watch('currentPage', function(c) {
                    scope.inputPage = c;
                });
            }
        };
    });