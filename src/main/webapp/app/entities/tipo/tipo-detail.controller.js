(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('TipoDetailController', TipoDetailController);

    TipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tipo', 'ReservaTipo'];

    function TipoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tipo, ReservaTipo) {
        var vm = this;

        vm.tipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:tipoUpdate', function(event, result) {
            vm.tipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
