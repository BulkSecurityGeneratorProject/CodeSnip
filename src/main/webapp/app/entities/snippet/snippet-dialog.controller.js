(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('SnippetDialogController', SnippetDialogController);

    SnippetDialogController.$inject = ['$timeout', '$state', '$scope', '$stateParams', 'entity', 'Snippet', 'Comment', 'Rating', 'User', 'ProgrammingLanguage'];

    function SnippetDialogController ($timeout, $state, $scope, $stateParams, entity, Snippet, Comment, Rating, User, ProgrammingLanguage) {
        var vm = this;

        vm.snippet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comments = Comment.query();
        vm.ratings = Rating.query();
        vm.users = User.query();
        vm.programminglanguages = ProgrammingLanguage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
        }

        function save () {
            vm.isSaving = true;
            if (vm.snippet.id !== null) {
                Snippet.update(vm.snippet, onSaveSuccess, onSaveError);
            } else {
                Snippet.save(vm.snippet, onSaveSuccess, onSaveError);
            }

            $state.go('home');
        }

        function onSaveSuccess (result) {
            $scope.$emit('codeSnipApp:snippetUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
