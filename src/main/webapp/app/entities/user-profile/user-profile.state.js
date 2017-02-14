(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-profile', {
            parent: 'entity',
            url: '/user-profile?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserProfiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-profile/user-profiles.html',
                    controller: 'UserProfileController',
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
        .state('user-profile-detail', {
            parent: 'user-profile',
            url: '/user-profile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserProfile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-profile/user-profile-detail.html',
                    controller: 'UserProfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserProfile', function($stateParams, UserProfile) {
                    return UserProfile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-profile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-profile-detail.edit', {
            parent: 'user-profile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-profile/user-profile-dialog.html',
                    controller: 'UserProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserProfile', function(UserProfile) {
                            return UserProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-profile.new', {
            parent: 'user-profile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views : {
                'content@': {
                    templateUrl: 'app/entities/user-profile/user-profile-dialog.html',
                    controller: 'UserProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        phone: null,
                        address: null,
                        profileImage: null,
                        profileImageContentType: null,
                        id: null
                    };
                }
            }

        })
        .state('user-profile.edit', {
            parent: 'user-profile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-profile/user-profile-dialog.html',
                    controller: 'UserProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserProfile', function(UserProfile) {
                            return UserProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-profile', null, { reload: 'user-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-profile.delete', {
            parent: 'user-profile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-profile/user-profile-delete-dialog.html',
                    controller: 'UserProfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserProfile', function(UserProfile) {
                            return UserProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-profile', null, { reload: 'user-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
