(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beneficio', {
            parent: 'entity',
            url: '/beneficio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.beneficio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficio/beneficios.html',
                    controller: 'BeneficioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('beneficio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('beneficio-detail', {
            parent: 'entity',
            url: '/beneficio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coffeeShopApp.beneficio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficio/beneficio-detail.html',
                    controller: 'BeneficioDetailController',
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
                        name: $state.current.name || 'beneficio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('beneficio-detail.edit', {
            parent: 'beneficio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficio-dialog.html',
                    controller: 'BeneficioDialogController',
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
        .state('beneficio.new', {
            parent: 'beneficio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficio-dialog.html',
                    controller: 'BeneficioDialogController',
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
                    $state.go('beneficio', null, { reload: 'beneficio' });
                }, function() {
                    $state.go('beneficio');
                });
            }]
        })
        .state('beneficio.edit', {
            parent: 'beneficio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficio-dialog.html',
                    controller: 'BeneficioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beneficio', function(Beneficio) {
                            return Beneficio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficio', null, { reload: 'beneficio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beneficio.delete', {
            parent: 'beneficio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficio/beneficio-delete-dialog.html',
                    controller: 'BeneficioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Beneficio', function(Beneficio) {
                            return Beneficio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficio', null, { reload: 'beneficio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
