(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioAngDeleteController',BeneficioAngDeleteController);

    BeneficioAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Beneficio'];

    function BeneficioAngDeleteController($uibModalInstance, entity, Beneficio) {
        var vm = this;

        vm.beneficio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Beneficio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
