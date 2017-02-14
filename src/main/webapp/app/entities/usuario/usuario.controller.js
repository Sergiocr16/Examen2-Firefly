(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('UsuarioController', UsuarioController);

    UsuarioController.$inject = ['$scope', '$state', 'Usuario'];

    function UsuarioController ($scope, $state, Usuario) {
        var vm = this;

        vm.usuarios = [];

        loadAll();

        function loadAll() {
            Usuario.query(function(result) {
                vm.usuarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
