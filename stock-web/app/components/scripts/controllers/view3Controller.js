/* global console: true */
/* global _: true */
'use strict';
var app = angular.module('hgsys');

// view3 controller
app.controller('View3Ctrl', ['$scope', '$q', 'searchService', 'mysqlService',
function($scope, $q, searchService, mysqlService) {

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

        var dataWeekly = mysqlService.weekly(symbol),
            dataDaily = mysqlService.daily(symbol),
            data60min = mysqlService.minutes60(symbol),
            data30min = mysqlService.minutes30(symbol),
            data15min = mysqlService.minutes15(symbol);

        $q.all([dataWeekly, dataDaily, data60min, data30min, data15min]).then(function(values) {
            // assign daily data for ticker summary
            $scope.selectedSymbol.record = values[1].data;

            var sensiblePrices = [];
            _.forEach(values, function(value, idx) {
                sensiblePrices = sensiblePrices.concat(assemblePrices(value.data, idx + 1));
            });
            $scope.detailReady = true;
            $scope.selectedSymbol.sensiblePrices = _.sortByOrder(sensiblePrices, ['price'], ['desc']);
        });
    }

    var deviateTargets = ['ma5', 'ma10', 'ma15', 'ma20', 'ma25', 'ma30',
                          'ema17', 'ema34', 'ema55',
                          'bu25', 'bl25', 'bu55', 'bl55', 'bu99', 'bl99'];

    // level 0 --- monthly
    // level 1 --- weekly
    // level 2 --- daily
    // level 3 --- 60 minutes
    // level 4 --- 30 minutes
    // level 5 --- 15 minutes
    function assemblePrices(records, level) {
        var neededPriceKeys = [
                               'ma5', 'ma10', 'ma15', 'ma20', 'ma25', 'ma30', 'ma55', 'ma60', 'ma99', 'ma120', 'ma250', 'ma888',
                               'ema17', 'ema34', 'ema55',
                               'bl25', 'bu25', 'bl55', 'bu55', 'bl99', 'bu99'
                              ];

        // only save one 'close'
        if(level === 2) {
            neededPriceKeys.push('close');
        }

        return _.sortByOrder(_.map(_.pick(records, neededPriceKeys), function(value, key) {
            return {
                name: key.toUpperCase(),
                price: value,
                percentageToClose: (value - records.close) / records.close * 100,
                deviate: deviateTargets.indexOf(key) !== -1,
                level: level
            };
        }), ['price'], ['desc']);
    }

    // var regPeriod = new RegExp(/\d+$/);
    var topSize = 10;
    $scope.clickDeviate = function(selectedSymbol, priceObj) {
        var deviateName = 'deviate-' + priceObj.name.toLowerCase();

        var searchDto = {
            index: 'stock-tushare',
            type: 'data-history',
            query: {
                'term': {
                    'code': {
                        'value': selectedSymbol.record.code
                    }
                }
            },
            size: topSize
        };

        searchDto.sort = {};
        searchDto.sort[deviateName] = { 'order': 'desc' };

        searchService.search(searchDto).then(function(result) {
            $scope.deviatePopover = {
                'title': priceObj.name + ' - 乖离率 TOP' + topSize,
                'content': result.records,
                'key': deviateName,
                'currentDate': selectedSymbol.record.date,
                'container': 'body'
            };
        });
    };

    $scope.levelToText = function(level) {
        switch(level) {
            case 0: return '月';
            case 1: return '周';
            case 2: return '日';
            case 3: return '60M';
            case 4: return '30M';
            case 5: return '15M';
            default: return '未知';
        }
    };
}]);