(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaDeleteController',ReservaDeleteController);

    ReservaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reserva'];

    function ReservaDeleteController($uibModalInstance, entity, Reserva) {
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
