(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaDeleteController',EntradaDeleteController);

    EntradaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrada'];

    function EntradaDeleteController($uibModalInstance, entity, Entrada) {
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
