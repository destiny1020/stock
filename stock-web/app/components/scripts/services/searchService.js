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
            index = searchDto.index || 'stock',
            type = searchDto.type,
            size = searchDto.size || 10,
            sort = searchDto.sort || {},
            from = searchDto.from || 0;

        // append common sorting behaviors
        sort._id = { 'order' : 'asc' };
        sort._score = { 'order' : 'desc' };

        var searchJson = searchDto.json || {
                'index': index,
                'body': {
                    'size': size,
                    'from': from,
                    'query': query,
                    'sort': sort
                }
            };

        // append type if any
        if(type) {
            searchJson.type = type;
        }

        // TODO: extract the hardcoded index and type
        client.search(searchJson).then(function(result) {
            var ii = 0, hitsArray, hits_out = [];
            var hitsObject = result.hits || {};
            hitsArray = hitsObject.hits || [];
            for(;ii < hitsArray.length; ii++){
                hits_out.push(hitsArray[ii]._source);
            }

            deferred.resolve({
                total: hitsObject.total,
                records: hits_out
            });
        }, deferred.reject);

        return deferred.promise;
    };

    return {
        'search': search
    };

}]);