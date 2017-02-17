(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaAngDeleteController',EntradaAngDeleteController);

    EntradaAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrada'];

    function EntradaAngDeleteController($uibModalInstance, entity, Entrada) {
        var vm = this;

        vm.entrada = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entrada.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
