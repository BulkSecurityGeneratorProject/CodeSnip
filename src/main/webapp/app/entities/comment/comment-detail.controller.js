(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('CommentDetailController', CommentDetailController);

    CommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comment', 'User', 'Snippet'];

    function CommentDetailController($scope, $rootScope, $stateParams, previousState, entity, Comment, User, Snippet) {
        var vm = this;

        vm.comment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeSnipApp:commentUpdate', function(event, result) {
            vm.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
