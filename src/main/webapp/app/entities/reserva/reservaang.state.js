(function() {
    'use strict';

    angular
        .module('coffeeShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('misReservasang', {
            parent: 'entity',
            url: '/misReservaang?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'coffeeShopApp.reserva.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva/misreservasang.html',
                    controller: 'MisReservasAngController',
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
                    $translatePartialLoader.addPart('reserva');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('misentregasang', {
                    parent: 'entity',
                    url: '/misentregasang?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ADMIN'],
                        pageTitle: 'coffeeShopApp.reserva.home.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reserva/misentregasang.html',
                            controller: 'MisEntregasController',
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
                            $translatePartialLoader.addPart('reserva');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        }]
                    }
                })
                .state('reservaang', {
                    parent: 'entity',
                    url: '/reservaang?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ADMIN'],
                        pageTitle: 'coffeeShopApp.reserva.home.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reserva/reservasang.html',
                            controller: 'ReservaAngController',
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
                            $translatePartialLoader.addPart('reserva');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        }]
                    }
                })
        .state('reservaang-detail', {
            parent: 'entity',
            url: '/reservaang/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'coffeeShopApp.reserva.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reserva/reservaang-detail.html',
                    controller: 'ReservaAngDetailController',
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
                        name: $state.current.name || 'reservaang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
          .state('misreservasang-detail', {
                    parent: 'entity',
                    url: '/misreservasang/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                        pageTitle: 'coffeeShopApp.reserva.detail.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reserva/misreservasang-detail.html',
                            controller: 'ReservaAngDetailController',
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
                                name: $state.current.name || 'reservaang',
                                params: $state.params,
                                url: $state.href($state.current.name, $state.params)
                            };
                            return currentStateData;
                        }]
                    }
                })
        .state('reservaang-detail.edit', {
            parent: 'reservaang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reservaang-dialog.html',
                    controller: 'ReservaAngDialogController',
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
        .state('reservaang.new', {
            parent: 'misReservasang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reservaang-dialog.html',
                    controller: 'ReservaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaEntrega: null,
                                recursivo: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reservaang', null, { reload: 'reservaang' });
                }, function() {
                    $state.go('reservaang');
                });
            }]
        })
        .state('reservaang.edit', {
            parent: 'reservaang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reservaang-dialog.html',
                    controller: 'ReservaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reserva', function(Reserva) {
                            return Reserva.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservaang', null, { reload: 'reservaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reservaang.delete', {
            parent: 'reservaang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reserva/reservaang-delete-dialog.html',
                    controller: 'ReservaAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reserva', function(Reserva) {
                            return Reserva.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservaang', null, { reload: 'reservaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
