/* global console: true */
'use strict';
var app = angular.module('hgsys');

// view1 controller
app.controller('View1Ctrl', ['$scope', 'searchService',
    function($scope, searchService) {
        var searchDto = {
            query: {
                match_all: {
                    //symbol: "SH600588"
                }
            },
            type: "daily-20150717"
        };

        searchService.search(searchDto).then(function(result) {
            $scope.stocks = result.records;
        });
    }
]);