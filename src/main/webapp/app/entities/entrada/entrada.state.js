(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entrada', {
            parent: 'entity',
            url: '/entrada',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.entrada.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrada/entradas.html',
                    controller: 'EntradaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrada');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entrada-detail', {
            parent: 'entity',
            url: '/entrada/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.entrada.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrada/entrada-detail.html',
                    controller: 'EntradaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrada');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entrada', function($stateParams, Entrada) {
                    return Entrada.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entrada',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entrada-detail.edit', {
            parent: 'entrada-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entrada-dialog.html',
                    controller: 'EntradaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrada', function(Entrada) {
                            return Entrada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrada.new', {
            parent: 'entrada',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entrada-dialog.html',
                    controller: 'EntradaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                kg: null,
                                fecha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entrada', null, { reload: 'entrada' });
                }, function() {
                    $state.go('entrada');
                });
            }]
        })
        .state('entrada.edit', {
            parent: 'entrada',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entrada-dialog.html',
                    controller: 'EntradaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrada', function(Entrada) {
                            return Entrada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrada', null, { reload: 'entrada' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrada.delete', {
            parent: 'entrada',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entrada-delete-dialog.html',
                    controller: 'EntradaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entrada', function(Entrada) {
                            return Entrada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrada', null, { reload: 'entrada' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
