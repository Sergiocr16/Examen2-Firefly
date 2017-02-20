(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .controller('ReservaAngController', ReservaAngController);

    ReservaAngController.$inject = ['$scope', '$state', 'Reserva', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function ReservaAngController ($scope, $state, Reserva, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {

            Reserva.requestForTomorrow({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
             }, onSuccessForTomorrow, onError);
             Reserva.requests({
                             page: pagingParams.page - 1,
                             size: vm.itemsPerPage,
                             sort: sort()
                         }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccessForTomorrow(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.requestsForTomorrow = data;
                vm.page = pagingParams.page;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.reservas = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
