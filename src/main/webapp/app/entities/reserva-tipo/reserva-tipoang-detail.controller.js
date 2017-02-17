(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaTipoAngDetailController', ReservaTipoAngDetailController);

    ReservaTipoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReservaTipo', 'Tipo', 'Reserva'];

    function ReservaTipoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, ReservaTipo, Tipo, Reserva) {
        var vm = this;

        vm.reservaTipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:reservaTipoUpdate', function(event, result) {
            vm.reservaTipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
