(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaDialogController', ReservaDialogController);

    ReservaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reserva', 'ReservaTipo', 'Usuario'];

    function ReservaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reserva, ReservaTipo, Usuario) {
        var vm = this;

        vm.reserva = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.reservatipos = ReservaTipo.query();
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reserva.id !== null) {
                Reserva.update(vm.reserva, onSaveSuccess, onSaveError);
            } else {
                Reserva.save(vm.reserva, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:reservaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaEntrega = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
