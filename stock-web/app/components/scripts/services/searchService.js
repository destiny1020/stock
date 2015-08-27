'use strict';
var app = angular.module('hgsys');

app.factory('searchService', ['$q', 'esFactory', '$location', function($q, elasticsearch, $location){

    var client = elasticsearch({
        host: $location.host() + ':9200'
    });

    /**
     * Given a term and an offset(page), load another round of 10 stocks.
     *
     * Returns a promise.
     */
    var search = function(searchDto){
        var deferred = $q.defer();

        var query = searchDto.query,
            aggs = searchDto.aggs,
            index = searchDto.index || 'stock',
            type = searchDto.type,
            size = searchDto.size,
            sort = searchDto.sort,
            from = searchDto.from,
            searchType = searchDto.searchType;

        var searchJson = searchDto.json || {
                'index': index,
                'body': {
                    'query': query,
                    'aggs': aggs,
                    'sort': sort
                }
            };

        // append query if any
        if(query) {
            searchJson.body.query = query;
        }

        // append sort if any
        if(sort && !searchType) {
            // append common sorting behaviors
            sort._id = { 'order' : 'asc' };
            sort._score = { 'order' : 'desc' };
            searchJson.body.sort = sort;
        }

        // append type if any
        if(type) {
            searchJson.type = type;
        }

        // append search type if any
        if(searchType) {
            searchJson.searchType = searchType;
        }

        // append size and from if any
        if(size) {
            searchJson.body.size = size;
        }

        if(from) {
            searchJson.body.from = from;
        }

        // append aggs if any
        if(aggs) {
            searchJson.body.aggs = aggs;
        }

        client.search(searchJson).then(function(result) {
            var ii = 0, hitsArray, hits_out = [];
            var hitsObject = result.hits || {};
            hitsArray = hitsObject.hits || [];
            for(;ii < hitsArray.length; ii++){
                hits_out.push(hitsArray[ii]._source);
            }

            deferred.resolve({
                total: hitsObject.total,
                records: hits_out,
                aggregations: result.aggregations
            });
        }, deferred.reject);

        return deferred.promise;
    };

    return {
        'search': search
    };

}]);