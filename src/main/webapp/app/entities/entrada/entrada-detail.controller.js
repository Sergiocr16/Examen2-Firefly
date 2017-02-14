(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaDetailController', EntradaDetailController);

    EntradaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrada', 'Beneficio', 'Tipo'];

    function EntradaDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrada, Beneficio, Tipo) {
        var vm = this;

        vm.entrada = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:entradaUpdate', function(event, result) {
            vm.entrada = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
