(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('ProgrammingLanguageDetailController', ProgrammingLanguageDetailController);

    ProgrammingLanguageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProgrammingLanguage', 'Snippet'];

    function ProgrammingLanguageDetailController($scope, $rootScope, $stateParams, previousState, entity, ProgrammingLanguage, Snippet) {
        var vm = this;

        vm.programmingLanguage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeSnipApp:programmingLanguageUpdate', function(event, result) {
            vm.programmingLanguage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
