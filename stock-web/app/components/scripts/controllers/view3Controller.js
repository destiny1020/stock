/* global console: true */
/* global _: true */
'use strict';
var app = angular.module('hgsys');

// view1 controller
app.controller('View3Ctrl', ['$scope', 'searchService',
function($scope, searchService) {

    $scope.symbolCandates = [];
    $scope.autocomplete = function(symbol) {
        var keywords = [];
        var searchDto = {
            index: 'stock_constant',
            query: {
                'wildcard': {
                    'symbol': {
                        'value': '*' + symbol + '*'
                    }
                }
            },
            size: 10
        };

        return searchService.search(searchDto).then(function(result) {
            _.forEach(result.records, function(datum) {
                keywords.push({
                    'label': datum.symbol + ' --- ' + datum.name,
                    'symbol': datum.symbol,
                    'name': datum.name
                });
            });

            return keywords;
        });
    };

    $scope.selectSymbol = function($item, $model, $label) {
        $scope.selectedSymbol = $item;

        searchSymbolData($scope.selectedSymbol);
    };

    function searchSymbolData(selectedSymbol) {
        $scope.detailReady = false;
        // default to search the last trading day
        var symbol = selectedSymbol.symbol.substring(2);

        var searchDto = {
            index: 'stock-tushare',
            query: {
                'term': {
                    'code': {
                        'value': symbol
                    }
                }
            },
            sort: {
                'date': {
                    'order': 'desc'
                }
            },
            size: 1
        };

        searchService.search(searchDto).then(function(result) {
            if(result.records && result.records.length === 1) {
                $scope.detailReady = true;
                $scope.selectedSymbol.record = result.records[0];
            }
        });
    }

}]);