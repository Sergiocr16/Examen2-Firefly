(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('BeneficioAngDialogController', BeneficioAngDialogController);

    BeneficioAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Beneficio'];

    function BeneficioAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Beneficio) {
        var vm = this;

        vm.beneficio = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.beneficio.id !== null) {
                Beneficio.update(vm.beneficio, onSaveSuccess, onSaveError);
            } else {
                Beneficio.save(vm.beneficio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:beneficioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
