(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('TipoDeleteController',TipoDeleteController);

    TipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tipo'];

    function TipoDeleteController($uibModalInstance, entity, Tipo) {
        var vm = this;

        vm.tipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
