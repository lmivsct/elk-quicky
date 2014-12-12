// We define an EsConnector module that depends on the elasticsearch module.     
var EsConnector = angular.module('EsConnector', ['elasticsearch']);

// Create the es service from the esFactory
EsConnector.service('es', function (esFactory) {
    return esFactory({ host: 'dockervm:9200' });
});

// We define an Angular controller that returns the server health
// Inputs: $scope and the 'es' service

EsConnector.controller('ServerHealthController', function ($scope, es) {

    es.cluster.health(function (err, resp) {
        if (err) {
            $scope.data = err.message;
        } else {
            $scope.data = resp;
        }
    });
});

// We define an Angular controller that returns query results,
// Inputs: $scope and the 'es' service

EsConnector.controller('QueryController', function ($scope, es) {

    $scope.searchTerms = "";

    $scope.searchGare = function () {
        // search for documents
        es.search({
            index: 'quicky',
            size: 10,
            type: 'gare',
            body: {
                "query": {
                    "match": {
                        "nom": $scope.searchTerms
                    }
                }
            }
        }).then(function (response) {
            $scope.hitsGare = response.hits.hits;
        });
    }

    $scope.searchHotel = function (lat, lon) {
        // search for documents
        es.search({
            index: 'quicky',
            size: 20000,
            type: 'hotel',
            body: {
                "query": {
                    "filtered": {
                        "query": {
                            "match_all": {}
                        },
                        "filter": {
                            "geo_distance": {
                                "distance": "20km",
                                "location": {
                                    "lat": lat,
                                    "lon": lon
                                }
                            }
                        }
                    }
                },
                "sort" : [
                    {
                        "_geo_distance" : {
                            "location" : [lat, lon],
                            "order" : "asc",
                            "unit" : "km",
                            "mode" : "min",
                            "distance_type" : "arc"
                        }
                    }
                ]
            }

        }).then(function (response) {
            $scope.hitsHotels = response.hits.hits;
        });
    }

});