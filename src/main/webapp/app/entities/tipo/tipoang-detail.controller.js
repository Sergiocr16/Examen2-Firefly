(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('TipoAngDetailController', TipoAngDetailController);

    TipoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tipo'];

    function TipoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Tipo) {
        var vm = this;

        vm.tipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coffeeShopApp:tipoUpdate', function(event, result) {
            vm.tipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
