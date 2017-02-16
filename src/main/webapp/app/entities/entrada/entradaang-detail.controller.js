(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaAngDetailController', EntradaAngDetailController);

    EntradaAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrada', 'Tipo', 'Beneficio'];

    function EntradaAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrada, Tipo, Beneficio) {
        var vm = this;

        vm.entrada = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:entradaUpdate', function(event, result) {
            vm.entrada = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
