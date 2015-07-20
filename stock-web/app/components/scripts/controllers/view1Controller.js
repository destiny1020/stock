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
            type: "daily-20150720",
            sort: {
                'percentage': 'desc'
            }
        };

        $scope.stocks = [];

        // table related
        $scope.callServer = function(tableState) {
            $scope.isLoading = true;
            var pagination = tableState.pagination;

            var from = pagination.start || 0;     // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            var number = pagination.number || 10;  // Number of entries showed per page.

            // assemble the search dto
            searchDto.size = number;
            searchDto.from = from;

            // assemble sort if any
            if(tableState.sort.predicate) {
                searchDto.sort = {};
                searchDto.sort[tableState.sort.predicate] = {
                    'order' : tableState.sort.reverse ? 'desc' : 'asc'
                };
            }

            searchService.search(searchDto).then(function(result) {
                $scope.stocks = result.records;
                //tableState.pagination.numberOfPages = result.numberOfPages;//set the number of pages so the pagination can update
                tableState.pagination.numberOfPages = Math.ceil(result.total / number);//set the number of pages so the pagination can update
                $scope.isLoading = false;
            });
        };
    }
]);