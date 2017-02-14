(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaController', EntradaController);

    EntradaController.$inject = ['$scope', '$state', 'Entrada'];

    function EntradaController ($scope, $state, Entrada) {
        var vm = this;

        vm.entradas = [];

        loadAll();

        function loadAll() {
            Entrada.query(function(result) {
                vm.entradas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
