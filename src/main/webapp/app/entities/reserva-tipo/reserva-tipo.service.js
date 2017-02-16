(function() {
    'use strict';
    angular
        .module('coffeeShopApp')
        .factory('ReservaTipo', ReservaTipo);

    ReservaTipo.$inject = ['$resource'];

    function ReservaTipo ($resource) {
        var resourceUrl =  'api/reserva-tipos/:id';

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
