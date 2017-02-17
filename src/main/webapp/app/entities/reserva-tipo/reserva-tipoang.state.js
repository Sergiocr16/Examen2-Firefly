(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reserva-tipoang', {
            parent: 'entity',
            url: '/reserva-tipoang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reservaTipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva-tipo/reserva-tiposang.html',
                    controller: 'ReservaTipoAngController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservaTipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reserva-tipoang-detail', {
            parent: 'entity',
            url: '/reserva-tipoang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reservaTipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipoang-detail.html',
                    controller: 'ReservaTipoAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservaTipo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReservaTipo', function($stateParams, ReservaTipo) {
                    return ReservaTipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reserva-tipoang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reserva-tipoang-detail.edit', {
            parent: 'reserva-tipoang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipoang-dialog.html',
                    controller: 'ReservaTipoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReservaTipo', function(ReservaTipo) {
                            return ReservaTipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reserva-tipoang.new', {
            parent: 'reserva-tipoang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipoang-dialog.html',
                    controller: 'ReservaTipoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cantidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reserva-tipoang', null, { reload: 'reserva-tipoang' });
                }, function() {
                    $state.go('reserva-tipoang');
                });
            }]
        })
        .state('reserva-tipoang.edit', {
            parent: 'reserva-tipoang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipoang-dialog.html',
                    controller: 'ReservaTipoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReservaTipo', function(ReservaTipo) {
                            return ReservaTipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva-tipoang', null, { reload: 'reserva-tipoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reserva-tipoang.delete', {
            parent: 'reserva-tipoang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipoang-delete-dialog.html',
                    controller: 'ReservaTipoAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReservaTipo', function(ReservaTipo) {
                            return ReservaTipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva-tipoang', null, { reload: 'reserva-tipoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
