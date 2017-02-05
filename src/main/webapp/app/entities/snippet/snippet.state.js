(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('snippet', {
            parent: 'entity',
            url: '/snippet?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Snippets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/snippet/snippets.html',
                    controller: 'SnippetController',
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
        .state('snippet-detail', {
            parent: 'snippet',
            url: '/snippet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Snippet'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/snippet/snippet-detail.html',
                    controller: 'SnippetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Snippet', function($stateParams, Snippet) {
                    return Snippet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'snippet',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('snippet-detail.edit', {
            parent: 'snippet-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/snippet/snippet-dialog.html',
                    controller: 'SnippetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Snippet', function(Snippet) {
                            return Snippet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('snippet.new', {
            parent: 'snippet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/snippet/snippet-dialog.html',
                    controller: 'SnippetDialogController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                entity: function () {
                    return {
                        description: null,
                        snippet: null,
                        url: null,
                        durationInMinutes: null,
                        commentsBlocked: null,
                        id: null
                    };
                }
            }
        })
        .state('snippet.edit', {
            parent: 'snippet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/snippet/snippet-dialog.html',
                    controller: 'SnippetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Snippet', function(Snippet) {
                            return Snippet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('snippet', null, { reload: 'snippet' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('snippet.delete', {
            parent: 'snippet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/snippet/snippet-delete-dialog.html',
                    controller: 'SnippetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Snippet', function(Snippet) {
                            return Snippet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('snippet', null, { reload: 'snippet' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
