(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('RatingDialogController', RatingDialogController);

    RatingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rating', 'User', 'Snippet'];

    function RatingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rating, User, Snippet) {
        var vm = this;

        vm.rating = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.snippets = Snippet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rating.id !== null) {
                Rating.update(vm.rating, onSaveSuccess, onSaveError);
            } else {
                Rating.save(vm.rating, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('codeSnipApp:ratingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
