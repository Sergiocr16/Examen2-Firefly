(function() {
    'use strict';
    angular
        .module('coffeeShopApp')
        .factory('Reserva', Reserva);

    Reserva.$inject = ['$resource', 'DateUtils'];

    function Reserva ($resource, DateUtils) {
        var resourceUrl =  'api/reservas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaEntrega = DateUtils.convertDateTimeFromServer(data.fechaEntrega);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
             'getByUser': {
                 method:'GET',
                  url: 'api/reservasByCurrentUser'
                  , isArray: true
              },
               'delivers': {
                    method:'GET',
                    url: 'api/delivers',
                    isArray: true
                },
                 'requestForTomorrow': {
                    method:'GET',
                    url: 'api/requestsForTomorrow',
                    isArray: true
                }
        });
    }
})();
