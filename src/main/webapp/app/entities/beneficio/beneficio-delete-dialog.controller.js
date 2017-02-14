(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioDeleteController',BeneficioDeleteController);

    BeneficioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Beneficio'];

    function BeneficioDeleteController($uibModalInstance, entity, Beneficio) {
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
