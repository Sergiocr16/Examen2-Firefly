(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('EntradaAngDialogController', EntradaAngDialogController);

    EntradaAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrada', 'Tipo', 'Beneficio'];

    function EntradaAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrada, Tipo, Beneficio) {
        var vm = this;

        vm.entrada = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tipos = Tipo.query();
        vm.beneficios = Beneficio.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrada.id !== null) {
                Entrada.update(vm.entrada, onSaveSuccess, onSaveError);
            } else {
                Entrada.save(vm.entrada, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:entradaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
