(function() {
    'use strict';
    angular
        .module('coffeeShopApp')
        .factory('Usuario', Usuario);

    Usuario.$inject = ['$resource'];

    function Usuario ($resource) {
        var resourceUrl =  'api/usuarios/:id';

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
