(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaAngDeleteController',ReservaAngDeleteController);

    ReservaAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reserva'];

    function ReservaAngDeleteController($uibModalInstance, entity, Reserva) {
        var vm = this;

        vm.reserva = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reserva.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
