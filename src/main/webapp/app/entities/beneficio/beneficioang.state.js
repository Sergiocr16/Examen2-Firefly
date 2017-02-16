(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beneficioang', {
            parent: 'entity',
            url: '/beneficioang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.beneficio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficio/beneficiosang.html',
                    controller: 'BeneficioAngController',
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
                    $translatePartialLoader.addPart('beneficio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('beneficioang-detail', {
            parent: 'entity',
            url: '/beneficioang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.beneficio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficio/beneficioang-detail.html',
                    controller: 'BeneficioAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('beneficio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Beneficio', function($stateParams, Beneficio) {
                    return Beneficio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'beneficioang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('beneficioang-detail.edit', {
            parent: 'beneficioang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficioang-dialog.html',
                    controller: 'BeneficioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beneficio', function(Beneficio) {
                            return Beneficio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beneficioang.new', {
            parent: 'beneficioang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficioang-dialog.html',
                    controller: 'BeneficioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                localizacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beneficioang', null, { reload: 'beneficioang' });
                }, function() {
                    $state.go('beneficioang');
                });
            }]
        })
        .state('beneficioang.edit', {
            parent: 'beneficioang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficioang-dialog.html',
                    controller: 'BeneficioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beneficio', function(Beneficio) {
                            return Beneficio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficioang', null, { reload: 'beneficioang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beneficioang.delete', {
            parent: 'beneficioang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficioang-delete-dialog.html',
                    controller: 'BeneficioAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Beneficio', function(Beneficio) {
                            return Beneficio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficioang', null, { reload: 'beneficioang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
