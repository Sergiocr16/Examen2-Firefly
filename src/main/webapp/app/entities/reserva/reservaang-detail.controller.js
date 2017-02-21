(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaAngDetailController', ReservaAngDetailController);

    ReservaAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reserva', 'User','Tipo', 'ReservaTipo'];

    function ReservaAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Reserva, User, ReservaTipo,Tipo) {
        var vm = this;
         vm.reservatipos = Tipo.query();
         console.log(vm.reservatipos)
//    vm.loadPage = loadPage;
//        vm.predicate = pagingParams.predicate;
//        vm.reverse = pagingParams.ascending;
//        vm.transition = transition;
//        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.reserva = entity;

        vm.previousState = previousState.name;
               vm.getPrecioTipo = function(reservaTipo){

                         var variable = 0;
                           angular.forEach(vm.reservatipos, function(value, key) {
                                      if(value.id == reservaTipo.tipoId){

                                        variable = value.precioUnitario * reservaTipo.cantidad;
                                      }
                                    });
                                     return variable;
                     }

                       vm.getPrecioTotal = function(){
                                    var precioTotal = 0;
                                    var precioPorTipo = 0
                                    angular.forEach(vm.tipos, function(value, key) {
                                    precioPorTipo = precioPorTipo + vm.getPrecioTipo(value);

                                    });
                                     return precioPorTipo;

                             }
             loadAll()
             function loadAll () {
             Reserva.tiposByReserva({
                        id: vm.reserva.id
//                        page: pagingParams.page - 1,
//                        size: vm.itemsPerPage,
//                        sort: sort()
                    }, onSuccess, onError);

               }
          function onSuccess(data, headers) {
//                      vm.links = ParseLinks.parse(headers('link'));
//                      vm.totalItems = headers('X-Total-Count');
//                      vm.queryCount = vm.totalItems;
                  vm.tipos = data;
//                      vm.page = pagingParams.page;
                    console.log(vm.tipos);
               }
                  function onError(error) {
                      AlertService.error(error.data.message);
                  }
        var unsubscribe = $rootScope.$on('coffeeShopApp:reservaUpdate', function(event, result) {
            vm.reserva = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
