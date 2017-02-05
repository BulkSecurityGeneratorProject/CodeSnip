(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('RatingDetailController', RatingDetailController);

    RatingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rating', 'User', 'Snippet'];

    function RatingDetailController($scope, $rootScope, $stateParams, previousState, entity, Rating, User, Snippet) {
        var vm = this;

        vm.rating = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeSnipApp:ratingUpdate', function(event, result) {
            vm.rating = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
