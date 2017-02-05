(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rating', {
            parent: 'entity',
            url: '/rating?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ratings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rating/ratings.html',
                    controller: 'RatingController',
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
            }
        })
        .state('rating-detail', {
            parent: 'rating',
            url: '/rating/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rating'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rating/rating-detail.html',
                    controller: 'RatingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rating', function($stateParams, Rating) {
                    return Rating.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rating',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rating-detail.edit', {
            parent: 'rating-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rating/rating-dialog.html',
                    controller: 'RatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rating', function(Rating) {
                            return Rating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rating.new', {
            parent: 'rating',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rating/rating-dialog.html',
                    controller: 'RatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ratingType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rating', null, { reload: 'rating' });
                }, function() {
                    $state.go('rating');
                });
            }]
        })
        .state('rating.edit', {
            parent: 'rating',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rating/rating-dialog.html',
                    controller: 'RatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rating', function(Rating) {
                            return Rating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rating', null, { reload: 'rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rating.delete', {
            parent: 'rating',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rating/rating-delete-dialog.html',
                    controller: 'RatingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rating', function(Rating) {
                            return Rating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rating', null, { reload: 'rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
