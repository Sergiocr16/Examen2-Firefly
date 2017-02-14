(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo', {
            parent: 'entity',
            url: '/tipo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.tipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo/tipos.html',
                    controller: 'TipoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-detail', {
            parent: 'entity',
            url: '/tipo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.tipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo/tipo-detail.html',
                    controller: 'TipoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tipo', function($stateParams, Tipo) {
                    return Tipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-detail.edit', {
            parent: 'tipo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipo-dialog.html',
                    controller: 'TipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tipo', function(Tipo) {
                            return Tipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo.new', {
            parent: 'tipo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipo-dialog.html',
                    controller: 'TipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                precioUnitario: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo', null, { reload: 'tipo' });
                }, function() {
                    $state.go('tipo');
                });
            }]
        })
        .state('tipo.edit', {
            parent: 'tipo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipo-dialog.html',
                    controller: 'TipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tipo', function(Tipo) {
                            return Tipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo', null, { reload: 'tipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo.delete', {
            parent: 'tipo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipo-delete-dialog.html',
                    controller: 'TipoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tipo', function(Tipo) {
                            return Tipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo', null, { reload: 'tipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
