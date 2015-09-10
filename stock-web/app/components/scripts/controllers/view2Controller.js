/* global console: true */
/* global d3: true */
/* global _: true */
'use strict';
var app = angular.module('hgsys');

app.controller('View2Ctrl', ['$scope', 'searchService', 
function($scope, searchService) {
		$scope.options = {
        chart: {
            type: 'lineChart',
            height: 450,
            margin : {
                top: 20,
                right: 20,
                bottom: 40,
                left: 55
            },
            x: function(d){ return d.x; },
            y: function(d){ return d.y; },

            color: d3.scale.category10().range(),

            useInteractiveGuideline: true,
            interpolate: 'monotone',
            forceY: [-0.1, 0.1],
            dispatch: {
                stateChange: function(e){},
                changeState: function(e){},
                tooltipShow: function(e){},
                tooltipHide: function(e){}
            },
            xAxis: {
                axisLabel: '日期',
                tickFormat: function(d){
                    return formatter(new Date(mappingIdxToDate[d]));
                },
            },
            yAxis: {
                axisLabel: '涨幅(%)',
                tickFormat: function(d){
                    return d3.format('.3p')(d);
                },
                axisLabelDistance: 30
            },
            callback: function(chart){
            }
        },
        title: {
            enable: true,
            text: '板块运行示意图'
        },
        subtitle: {
            enable: false,
            text: '',
            css: {
                'text-align': 'center',
                'margin': '10px 13px 0px 7px'
            }
        },
        caption: {
            enable: false,
            html: '',
            css: {
                'text-align': 'justify',
                'margin': '10px 13px 0px 7px'
            }
        },
        updateWithOptions: true
    };

    var mappingIdxToDate = [];
    var mappingDateToIdx = [];
    var formatter = d3.time.format('%m-%d');

    // blockSelected should have form of [blockName1, blockName2]
    function getData(blockSelected) {
        if(blockSelected === null || 
            blockSelected === undefined) {
            return;
        }

        // result container
        var resultContainer = {};
        _.forEach(blockSelected, function(blockName, idx) {
            resultContainer[blockName] = [];
        });

    	var searchDto = {
    		index: 'index_block',
            query: {
                'filtered': {
                	'filter': {
                		'terms': {
                			'name.raw': blockSelected
                		}
                	}
                }
            },
            sort: {
                '_type': 'asc'
            },
            size: 500
        };

        searchService.search(searchDto).then(function(result) {
            // create mapping from idx ---> recordDate
            var allDates = _.chain(result.records).pluck('recordDate').unique().value();
            $scope.options.chart.forceX = [1, allDates.length];
            var indices = _.range(1, allDates.length + 1);
            mappingIdxToDate = _.zipObject(indices, allDates);
            mappingDateToIdx = _.invert(mappingIdxToDate);

            var results = d3.nest()
            	.key(function(d){ return d.name; })
            	.sortValues(function(a, b){ return a.recordDate - b.recordDate; })
            	.entries(result.records);

            var dailyMapping = function(daily) {
            	return {
            		x: mappingDateToIdx[daily.recordDate],
            		y: daily.percentage
            	};
            };
            results.forEach(function(block) {
                resultContainer[block.key] = block.values.map(dailyMapping);
            });

            var finalResults = [];
            _.forIn(resultContainer, function(value, key) {
                finalResults.push({
                    key: key,
                    values: value
                });
            });

            //Line chart data should be sent as an array of series objects.
            $scope.data = finalResults;
        });
    }

    function getBlockNames() {
        var searchDto = {
            index: 'index_block',
            searchType: 'count',
            aggs: {
                'uniq_blocks': {
                    'terms': {
                        'field': 'name.raw',
                        'size': 0
                    }
                }
            }
        };

        searchService.search(searchDto).then(function(result) {
            var aggName = _.keys(result.aggregations)[0];
            var blocks = result.aggregations[aggName].buckets;

            // append a tick property to the block array
            _.forEach(blocks, function(datum, idx) {
                if(defaultBlocks.indexOf(datum.key) !== -1) {
                    datum.ticked = true;
                } else {
                    datum.ticked = false;
                }
            });

            $scope.blockCandidates = blocks;
        });
    }

    $scope.blockSelect = function(block) {
       var selectedBlocks = _.pluck($scope.blockSelected, 'key');

       // limit the lines count limit to 4
       getData(selectedBlocks.splice(0, 4));
    };

    $scope.blockUnselect = function() {
        $scope.data = [];
    };

    // load for the first time
    var defaultBlocks = ['银行', '大飞机', '计算机应用'];
    $scope.blockSelected = _.map(defaultBlocks, function(block) {
        return {
            key: block
        };
    });

    $scope.blockClear = function() {
        console.log('haha');
        getData([]);
    };

    // load available dates from index_block
    function getAvailableDates() {
        var searchDto = {
            index: 'index_block',
            searchType: 'count',
            aggs: {
                'uniq_dates': {
                    'terms': {
                        'field': '_type',
                        'size': 0
                    }
                }
            }
        };

        searchService.search(searchDto).then(function(result) {
            var aggName = _.keys(result.aggregations)[0];
            var dates = result.aggregations[aggName].buckets;

            // append a tick property to the block array
            _.forEach(dates, function(datum, idx) {
                datum.key = datum.key.substring(datum.key.indexOf('-') + 1);
                datum.ticked = false;
            });

            // sort the dates by date
            dates = _.sortBy(dates, function(datum) {
                return -parseInt(datum.key, 10);
            });

            $scope.dateCandidates = dates;

            if(dates && dates.length > 0) {
                dates[0].ticked = true;
                $scope.dateSelected = [dates[0]];
            }
        });
    }

    $scope.dateSelect = function(date) {
        $scope.callServer($scope.tableState);
    };

    // get blocks data for certain date
    $scope.blocks = [];
    $scope.itemsByPage = 10;
    $scope.tableState = undefined;

    $scope.callServer = function(tableState) {
        var searchDto = {
            index: 'index_block',
            query: {
                match_all: {
                }
            },
            type: 'daily-' + $scope.dateSelected[0].key,
            sort: {
                'percentage': 'desc'
            }
        };

        $scope.isLoading = true;
        $scope.tableState = tableState;

        var pagination = tableState.pagination;

        var from = pagination.start || 0;     
        var number = pagination.number || 10;

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
            $scope.blocks = result.records;
            //tableState.pagination.numberOfPages = result.numberOfPages;//set the number of pages so the pagination can update
            tableState.pagination.numberOfPages = Math.ceil(result.total / number);//set the number of pages so the pagination can update
            $scope.isLoading = false;
        });
    };

    $scope.chooseItemsPerPage = function(itemByPage) {
        $scope.itemsByPage = itemByPage;

        // update pagination field
        $scope.tableState.pagination.number = itemByPage;

        $scope.callServer($scope.tableState);
    };

    // individual share typeahead support
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

    // init operations
    function init() {
        // available dates
        getAvailableDates();

        // block/codes selectors below
        getBlockNames();

        $scope.blockSelect();
    }

    init();
}]);