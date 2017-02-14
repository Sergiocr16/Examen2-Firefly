(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaTipoDialogController', ReservaTipoDialogController);

    ReservaTipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReservaTipo', 'Tipo', 'Reserva'];

    function ReservaTipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReservaTipo, Tipo, Reserva) {
        var vm = this;

        vm.reservaTipo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipos = Tipo.query();
        vm.reservas = Reserva.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reservaTipo.id !== null) {
                ReservaTipo.update(vm.reservaTipo, onSaveSuccess, onSaveError);
            } else {
                ReservaTipo.save(vm.reservaTipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:reservaTipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
