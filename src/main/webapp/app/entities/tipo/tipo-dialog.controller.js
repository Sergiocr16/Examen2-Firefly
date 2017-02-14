(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('TipoDialogController', TipoDialogController);

    TipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tipo', 'ReservaTipo'];

    function TipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tipo, ReservaTipo) {
        var vm = this;

        vm.tipo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reservatipos = ReservaTipo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipo.id !== null) {
                Tipo.update(vm.tipo, onSaveSuccess, onSaveError);
            } else {
                Tipo.save(vm.tipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:tipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
