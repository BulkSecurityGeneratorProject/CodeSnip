(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('login', {
            parent: 'account',
            url: '/login',
            data: {
                authorities: [],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
