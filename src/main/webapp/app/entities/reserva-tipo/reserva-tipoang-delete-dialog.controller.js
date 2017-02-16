(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaTipoAngDeleteController',ReservaTipoAngDeleteController);

    ReservaTipoAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReservaTipo'];

    function ReservaTipoAngDeleteController($uibModalInstance, entity, ReservaTipo) {
        var vm = this;

        vm.reservaTipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReservaTipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
