(function() {
    'use strict';
    angular
        .module('coffeeShopApp')
        .factory('Entrada', Entrada);

    Entrada.$inject = ['$resource', 'DateUtils'];

    function Entrada ($resource, DateUtils) {
        var resourceUrl =  'api/entradas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
