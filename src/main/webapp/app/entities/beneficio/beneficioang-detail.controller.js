(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioAngDetailController', BeneficioAngDetailController);

    BeneficioAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Beneficio'];

    function BeneficioAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Beneficio) {
        var vm = this;

        vm.beneficio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:beneficioUpdate', function(event, result) {
            vm.beneficio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
