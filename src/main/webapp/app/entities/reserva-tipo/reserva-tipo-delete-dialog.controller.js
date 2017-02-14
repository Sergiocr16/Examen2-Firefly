(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaTipoDeleteController',ReservaTipoDeleteController);

    ReservaTipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReservaTipo'];

    function ReservaTipoDeleteController($uibModalInstance, entity, ReservaTipo) {
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
