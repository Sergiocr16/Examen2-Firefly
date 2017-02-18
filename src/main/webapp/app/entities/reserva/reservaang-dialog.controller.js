(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaAngDialogController', ReservaAngDialogController);

    ReservaAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reserva', 'User', 'ReservaTipo','Tipo','Principal'];

    function ReservaAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reserva, User, ReservaTipo,Tipo,Principal) {
        var vm = this;

        vm.reserva = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.tipos = Tipo.query();
        vm.cantTipos = 1;
        vm.reservatipos = []

        vm.pushReservaTipos = function(){
            vm.reservatipos.push({cantidad:1, id:null, tipoId:null})

        }
        vm.pushReservaTipos();
        vm.removeReservaTipo = function(index){
              vm.reservatipos.splice(index,1)
        }
        vm.getPrecioTipo = function(reservaTipo){

            var variable = 0;
              angular.forEach(vm.tipos, function(value, key) {
                         if(value.id == reservaTipo.tipoId){

                           variable = value.precioUnitario * reservaTipo.cantidad;
                         }
                       });
                        return variable;
        }

          vm.getPrecioTotal = function(){
//                    var precioTotal = 0;
//                    var precioPorTipo = vm.reservatipos.map((reservaTipo)function(){return vm.getPrecioTipo(reservaTipo);})
//                     angular.forEach(precioPorTipo, function(value, key) {
//                             precioTotal = precioTotal + value;
//                       });
//                       return precioTotal;
                }

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
             Principal.identity().then(function(account) {
            vm.reserva.usuarioId = account.id;
                                          });
            vm.reserva.procesado = false;
                Reserva.save(vm.reserva, onSaveSuccess, onSaveError);
            }
        }


        function onSaveSuccess (result) {
            $scope.$emit('coffeeShopApp:reservaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            angular.forEach(vm.reservatipos, function(value, key) {
              value.reservaId = result.id;
              if(value.tipoId!=null){
                  ReservaTipo.save(value);

              }
            });
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
