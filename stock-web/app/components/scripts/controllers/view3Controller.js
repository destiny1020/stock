/* global console: true */
/* global _: true */
'use strict';
var app = angular.module('hgsys');

// view1 controller
app.controller('View3Ctrl', ['$scope', 'searchService',
function($scope, searchService) {

    $scope.selectedSymbols = [];
    // $scope.autocomplete = function(symbol) {
    //     var keywords = [];
    //     var searchDto = {
    //         index: 'stock_constant',
    //         query: {
    //             'wildcard': {
    //                 'symbol': {
    //                     'value': '*' + symbol + '*'
    //                 }
    //             }
    //         },
    //         size: 10
    //     };

    //     return searchService.search(searchDto).then(function(result) {
    //         _.forEach(result.records, function(datum) {
    //             keywords.push({
    //                 'label': datum.symbol + ' --- ' + datum.name,
    //                 'symbol': datum.symbol,
    //                 'name': datum.name
    //             });
    //         });

    //         return keywords;
    //     });
    // };

    $scope.loadShares = function($query) {
        var keywords = [];
        var searchDto = {
            index: 'stock_constant',
            query: {
                'wildcard': {
                    'symbol': {
                        'value': '*' + $query + '*'
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

    $scope.addingSymbol = function($tag) {
        if($scope.selectedSymbols.length >= 5) {
            return false;
        }

        return true;
    };

    $scope.addSymbol = function($tag) {
        $scope.selectedSymbols.push($tag);
        $scope.selectedSymbol = $tag;

        searchSymbolData($scope.selectedSymbol);
    };

    $scope.removeSymbol = function($tag) {
        _.remove($scope.selectedSymbols, function(symbolObj) {
            return symbolObj.symbol === $tag.symbol;
        });

        if($scope.selectedSymbol.symbol === $tag.symbol) {
            $scope.selectedSymbol = $scope.selectedSymbols[0];
        }
    };

    function searchSymbolData(symbolObj) {
        $scope.detailReady = false;
        // default to search the last trading day
        var symbol = symbolObj.symbol.substring(2);

        var searchDto = {
            index: 'stock-tushare',
            type: 'data-history',
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

                // assemble the sensible prices
                $scope.selectedSymbol.sensiblePrices = assemblePrices(result.records[0]);
            }
        });
    }

    function assemblePrices(records) {
        var neededPriceKeys = ['ma5', 'ma10', 'ma20', 'ma25', 'ma30', 'ma55', 'ma60', 'ma99', 'ma120', 'ma250', 'ma888',
                               'ema17', 'ema34', 'ema55',
                               'bl25', 'bu25', 'bl55', 'bu55', 'bl99', 'bu99',
                               'close'];
        return _.sortByOrder(_.map(_.pick(records, neededPriceKeys), function(value, key) {
            return {
                name: key.toUpperCase(),
                price: value,
                percentageToClose: (value - records['close']) / records['close'] * 100
            }
        }), ['price'], ['desc']);
    }

}]);