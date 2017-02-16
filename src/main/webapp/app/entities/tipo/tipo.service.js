(function() {
    'use strict';
    angular
        .module('coffeeShopApp')
        .factory('Tipo', Tipo);

    Tipo.$inject = ['$resource'];

    function Tipo ($resource) {
        var resourceUrl =  'api/tipos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
