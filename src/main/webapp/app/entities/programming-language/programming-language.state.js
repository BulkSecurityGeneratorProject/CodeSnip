(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('programming-language', {
            parent: 'entity',
            url: '/programming-language?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProgrammingLanguages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programming-language/programming-languages.html',
                    controller: 'ProgrammingLanguageController',
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
        .state('programming-language-detail', {
            parent: 'programming-language',
            url: '/programming-language/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProgrammingLanguage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programming-language/programming-language-detail.html',
                    controller: 'ProgrammingLanguageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProgrammingLanguage', function($stateParams, ProgrammingLanguage) {
                    return ProgrammingLanguage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'programming-language',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('programming-language-detail.edit', {
            parent: 'programming-language-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programming-language/programming-language-dialog.html',
                    controller: 'ProgrammingLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProgrammingLanguage', function(ProgrammingLanguage) {
                            return ProgrammingLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programming-language.new', {
            parent: 'programming-language',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programming-language/programming-language-dialog.html',
                    controller: 'ProgrammingLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('programming-language', null, { reload: 'programming-language' });
                }, function() {
                    $state.go('programming-language');
                });
            }]
        })
        .state('programming-language.edit', {
            parent: 'programming-language',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programming-language/programming-language-dialog.html',
                    controller: 'ProgrammingLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProgrammingLanguage', function(ProgrammingLanguage) {
                            return ProgrammingLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programming-language', null, { reload: 'programming-language' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programming-language.delete', {
            parent: 'programming-language',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programming-language/programming-language-delete-dialog.html',
                    controller: 'ProgrammingLanguageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProgrammingLanguage', function(ProgrammingLanguage) {
                            return ProgrammingLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programming-language', null, { reload: 'programming-language' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
