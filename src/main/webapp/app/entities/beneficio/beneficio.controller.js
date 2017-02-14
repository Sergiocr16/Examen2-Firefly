(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioController', BeneficioController);

    BeneficioController.$inject = ['$scope', '$state', 'Beneficio'];

    function BeneficioController ($scope, $state, Beneficio) {
        var vm = this;

        vm.beneficios = [];

        loadAll();

        function loadAll() {
            Beneficio.query(function(result) {
                vm.beneficios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
