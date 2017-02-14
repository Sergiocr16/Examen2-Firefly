(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaController', ReservaController);

    ReservaController.$inject = ['$scope', '$state', 'Reserva'];

    function ReservaController ($scope, $state, Reserva) {
        var vm = this;

        vm.reservas = [];

        loadAll();

        function loadAll() {
            Reserva.query(function(result) {
                vm.reservas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
