(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaDetailController', ReservaDetailController);

    ReservaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reserva', 'ReservaTipo', 'Usuario'];

    function ReservaDetailController($scope, $rootScope, $stateParams, previousState, entity, Reserva, ReservaTipo, Usuario) {
        var vm = this;

        vm.reserva = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:reservaUpdate', function(event, result) {
            vm.reserva = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
