(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reserva', {
            parent: 'entity',
            url: '/reserva',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reserva.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva/reservas.html',
                    controller: 'ReservaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reserva');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reserva-detail', {
            parent: 'entity',
            url: '/reserva/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.reserva.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva/reserva-detail.html',
                    controller: 'ReservaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reserva');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Reserva', function($stateParams, Reserva) {
                    return Reserva.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reserva',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reserva-detail.edit', {
            parent: 'reserva-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reserva-dialog.html',
                    controller: 'ReservaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reserva', function(Reserva) {
                            return Reserva.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reserva.new', {
            parent: 'reserva',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reserva-dialog.html',
                    controller: 'ReservaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaEntrega: null,
                                recursivo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reserva', null, { reload: 'reserva' });
                }, function() {
                    $state.go('reserva');
                });
            }]
        })
        .state('reserva.edit', {
            parent: 'reserva',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reserva-dialog.html',
                    controller: 'ReservaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reserva', function(Reserva) {
                            return Reserva.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva', null, { reload: 'reserva' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reserva.delete', {
            parent: 'reserva',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reserva-delete-dialog.html',
                    controller: 'ReservaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reserva', function(Reserva) {
                            return Reserva.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reserva', null, { reload: 'reserva' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
