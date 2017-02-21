(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entradaang', {
            parent: 'entity',
            url: '/entradaang?page&sort&search',
            data: {
                authorities: ['ROLE_INVENTORY'],
                pageTitle: 'coffeeShopApp.entrada.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrada/entradasang.html',
                    controller: 'EntradaAngController',
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
                    $translatePartialLoader.addPart('entrada');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entradaang-detail', {
            parent: 'entity',
            url: '/entradaang/{id}',
            data: {
                authorities: ['ROLE_INVENTORY'],
                pageTitle: 'coffeeShopApp.entrada.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrada/entradaang-detail.html',
                    controller: 'EntradaAngDetailController',
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
                        name: $state.current.name || 'entradaang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entradaang-detail.edit', {
            parent: 'entradaang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entradaang-dialog.html',
                    controller: 'EntradaAngDialogController',
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
        .state('entradaang.new', {
            parent: 'entradaang',
            url: '/new',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entradaang-dialog.html',
                    controller: 'EntradaAngDialogController',
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
                    $state.go('entradaang', null, { reload: 'entradaang' });
                }, function() {
                    $state.go('entradaang');
                });
            }]
        })
        .state('entradaang.edit', {
            parent: 'entradaang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entradaang-dialog.html',
                    controller: 'EntradaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrada', function(Entrada) {
                            return Entrada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entradaang', null, { reload: 'entradaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entradaang.delete', {
            parent: 'entradaang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrada/entradaang-delete-dialog.html',
                    controller: 'EntradaAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entrada', function(Entrada) {
                            return Entrada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entradaang', null, { reload: 'entradaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
