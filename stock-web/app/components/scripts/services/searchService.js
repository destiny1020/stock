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
    var search = function(searchDto, page){
        var deferred = $q.defer();

        var query = searchDto.query,
            type = searchDto.type,
            size = searchDto.size || 10;

        page = (page === undefined) ? currentPage() : page;

        var searchJson = searchDto.json || {
                'index': 'stock',
                'type': type,
                'body': {
                    'size': size,
                    'from': (page || 0) * size,
                    'query': query,
                    'sort': {
                        '_score': {
                            'order': 'desc'
                        },
                        '_id': {
                            'order': 'asc'
                        }
                    }
                }
            };

        // TODO: extract the hardcoded index and type
        client.search(searchJson).then(function(result) {
            var ii = 0, hitsArray, hits_out = [];
            var hitsObject = result.hits || {};
            hitsArray = hitsObject.hits || [];
            for(;ii < hitsArray.length; ii++){
                hits_out.push(hitsArray[ii]._source);
            }

            // set current actress page number
            _currentPage = page;

            deferred.resolve({
                total: hitsObject.total,
                records: hits_out
            });
        }, deferred.reject);

        return deferred.promise;
    };

    var _currentPage;
    var currentPage = function() {
        return _currentPage || 0;
    };

    return {
        'search': search,
        'currentPage': currentPage
    };

}]);