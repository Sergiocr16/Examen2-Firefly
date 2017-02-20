(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipoang', {
            parent: 'entity',
            url: '/tipoang?page&sort&search',
            data: {
                authorities: ['ROLE_INVENTORY'],
                pageTitle: 'coffeeShopApp.tipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo/tiposang.html',
                    controller: 'TipoAngController',
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
                    $translatePartialLoader.addPart('tipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipoang-detail', {
            parent: 'entity',
            url: '/tipoang/{id}',
            data: {
                authorities: ['ROLE_INVENTORY'],
                pageTitle: 'coffeeShopApp.tipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo/tipoang-detail.html',
                    controller: 'TipoAngDetailController',
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
                        name: $state.current.name || 'tipoang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipoang-detail.edit', {
            parent: 'tipoang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipoang-dialog.html',
                    controller: 'TipoAngDialogController',
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
        .state('tipoang.new', {
            parent: 'tipoang',
            url: '/new',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipoang-dialog.html',
                    controller: 'TipoAngDialogController',
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
                    $state.go('tipoang', null, { reload: 'tipoang' });
                }, function() {
                    $state.go('tipoang');
                });
            }]
        })
        .state('tipoang.edit', {
            parent: 'tipoang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipoang-dialog.html',
                    controller: 'TipoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tipo', function(Tipo) {
                            return Tipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipoang', null, { reload: 'tipoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipoang.delete', {
            parent: 'tipoang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_INVENTORY']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo/tipoang-delete-dialog.html',
                    controller: 'TipoAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tipo', function(Tipo) {
                            return Tipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipoang', null, { reload: 'tipoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
