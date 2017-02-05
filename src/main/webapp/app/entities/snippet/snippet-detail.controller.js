(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('SnippetDetailController', SnippetDetailController);

    SnippetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Snippet', 'Comment', 'Rating', 'User', 'ProgrammingLanguage'];

    function SnippetDetailController($scope, $rootScope, $stateParams, previousState, entity, Snippet, Comment, Rating, User, ProgrammingLanguage) {
        var vm = this;

        vm.snippet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeSnipApp:snippetUpdate', function(event, result) {
            vm.snippet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
