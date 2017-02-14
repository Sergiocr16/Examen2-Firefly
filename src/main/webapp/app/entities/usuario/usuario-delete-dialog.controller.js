(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('UsuarioDeleteController',UsuarioDeleteController);

    UsuarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Usuario'];

    function UsuarioDeleteController($uibModalInstance, entity, Usuario) {
        var vm = this;

        vm.usuario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Usuario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
