(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioDetailController', BeneficioDetailController);

    BeneficioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Beneficio', 'Entrada'];

    function BeneficioDetailController($scope, $rootScope, $stateParams, previousState, entity, Beneficio, Entrada) {
        var vm = this;

        vm.beneficio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:beneficioUpdate', function(event, result) {
            vm.beneficio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
