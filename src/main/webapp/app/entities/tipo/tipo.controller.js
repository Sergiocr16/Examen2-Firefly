(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('TipoController', TipoController);

    TipoController.$inject = ['$scope', '$state', 'Tipo'];

    function TipoController ($scope, $state, Tipo) {
        var vm = this;

        vm.tipos = [];

        loadAll();

        function loadAll() {
            Tipo.query(function(result) {
                vm.tipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
