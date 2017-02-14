(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reserva-tipo', {
            parent: 'entity',
            url: '/reserva-tipo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reservaTipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipos.html',
                    controller: 'ReservaTipoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservaTipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reserva-tipo-detail', {
            parent: 'entity',
            url: '/reserva-tipo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reservaTipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipo-detail.html',
                    controller: 'ReservaTipoDetailController',
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
                        name: $state.current.name || 'reserva-tipo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reserva-tipo-detail.edit', {
            parent: 'reserva-tipo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipo-dialog.html',
                    controller: 'ReservaTipoDialogController',
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
        .state('reserva-tipo.new', {
            parent: 'reserva-tipo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipo-dialog.html',
                    controller: 'ReservaTipoDialogController',
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
                    $state.go('reserva-tipo', null, { reload: 'reserva-tipo' });
                }, function() {
                    $state.go('reserva-tipo');
                });
            }]
        })
        .state('reserva-tipo.edit', {
            parent: 'reserva-tipo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipo-dialog.html',
                    controller: 'ReservaTipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReservaTipo', function(ReservaTipo) {
                            return ReservaTipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva-tipo', null, { reload: 'reserva-tipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reserva-tipo.delete', {
            parent: 'reserva-tipo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva-tipo/reserva-tipo-delete-dialog.html',
                    controller: 'ReservaTipoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReservaTipo', function(ReservaTipo) {
                            return ReservaTipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva-tipo', null, { reload: 'reserva-tipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
