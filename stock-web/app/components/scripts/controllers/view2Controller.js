/* global console: true */
/* global d3: true */
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
            interpolate: 'linear',
            dispatch: {
                stateChange: function(e){ console.log("stateChange"); },
                changeState: function(e){ console.log("changeState"); },
                tooltipShow: function(e){ console.log("tooltipShow"); },
                tooltipHide: function(e){ console.log("tooltipHide"); }
            },
            xAxis: {
                axisLabel: '日期'
            },
            yAxis: {
                axisLabel: '涨幅(%)',
                tickFormat: function(d){
                    return d3.format('.3p')(d);
                },
                axisLabelDistance: 30
            },
            callback: function(chart){
                console.log("!!! lineChart callback !!!");
            }
        },
        "legend": {
          "dispatch": {},
          "width": 200,
          "height": 20,
          "align": true,
          "rightAlign": true,
          "padding": 40,
          "updateState": true,
          "radioButtonMode": false,
          "expanded": false,
          "vers": "classic",
          "margin": {
            "top": 5,
            "right": 0,
            "bottom": 5,
            "left": 0
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
        }
    };

    getData();

    function getData() {
    	var searchDto = {
    		index: 'index_block',
            query: {
                'filtered': {
                	'filter': {
                		'terms': {
                			'name.raw': ['大飞机', '国防军工', '计算机应用', '医疗改革']
                		}
                	}
                }
            },
            sort: {
                '_type': 'asc'
            },
            size: 500
        };

        var dfj = [],
        	gfjg = [],
            jsjyy = [],
            ylgg = [];

        var formatter = d3.time.format("%Y-%m-%d");
        searchService.search(searchDto).then(function(result) {
            var results = d3.nest()
            	.key(function(d){ return d.name; })
            	.sortValues(function(a, b){ return a.recordDate - b.recordDate; })
            	.entries(result.records);

            var idx = 1;
            var dailyMapping = function(daily) {
            	return {
            		x: idx++,
            		y: daily.percentage
            	};
            };
            results.forEach(function(block) {
            	switch(block.key) {
            		case '大飞机':
            			dfj = block.values.map(dailyMapping);
            			idx = 1;
            			break;
            		case '国防军工':
            			gfjg = block.values.map(dailyMapping);
            			idx = 1;
            			break;
            		case '计算机应用':
            			jsjyy = block.values.map(dailyMapping);
            			idx = 1;
            			break;
            		case '医疗改革':
            			ylgg = block.values.map(dailyMapping);
            			idx = 1;
            			break;
            	}
            });

            console.log(dfj, gfjg, jsjyy, ylgg);

            //Line chart data should be sent as an array of series objects.
            $scope.data = [
                {
                    values: dfj,      //values - represents the array of {x,y} data points
                    key: '大飞机',    //key  - the name of the series.
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
}]);