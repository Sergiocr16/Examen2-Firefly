(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaTipoController', ReservaTipoController);

    ReservaTipoController.$inject = ['$scope', '$state', 'ReservaTipo'];

    function ReservaTipoController ($scope, $state, ReservaTipo) {
        var vm = this;

        vm.reservaTipos = [];

        loadAll();

        function loadAll() {
            ReservaTipo.query(function(result) {
                vm.reservaTipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
