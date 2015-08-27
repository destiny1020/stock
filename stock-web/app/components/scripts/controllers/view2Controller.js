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
            text: 'Block Indices'
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
    getData();

    function getData() {
    	var searchDto = {
    		index: 'index_block',
            query: {
                'filtered': {
                	'filter': {
                		'terms': {
                			'name.raw': ['银行', '国防军工', '计算机应用', '医疗改革']
                		}
                	}
                }
            },
            sort: {
                '_type': 'asc'
            },
            size: 500
        };

        var yh = [],
        	gfjg = [],
            jsjyy = [],
            ylgg = [];

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
            	switch(block.key) {
            		case '银行':
            			yh = block.values.map(dailyMapping);
            			break;
            		case '国防军工':
            			gfjg = block.values.map(dailyMapping);
            			break;
            		case '计算机应用':
            			jsjyy = block.values.map(dailyMapping);
            			break;
            		case '医疗改革':
            			ylgg = block.values.map(dailyMapping);
            			break;
            	}
            });

            //Line chart data should be sent as an array of series objects.
            $scope.data = [
                {
                    values: yh,      //values - represents the array of {x,y} data points
                    key: '银行',    //key  - the name of the series.
                },
                {
                    values: gfjg,
                    key: '国防军工'
                },
                {
                    values: jsjyy,
                    key: '计算机应用'
                },
                {
                    values: ylgg,
                    key: '医疗改革'
                }
            ];
        });
    }

    // block/codes selectors below
    getBlockNames();

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
                datum.ticked = false;
            });

            $scope.blockCandidates = blocks;
        });
    }
}]);