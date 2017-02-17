(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaAngDetailController', ReservaAngDetailController);

    ReservaAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reserva', 'User', 'ReservaTipo'];

    function ReservaAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Reserva, User, ReservaTipo) {
        var vm = this;

        vm.reserva = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:reservaUpdate', function(event, result) {
            vm.reserva = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
