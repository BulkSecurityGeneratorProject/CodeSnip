(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('ProgrammingLanguageDialogController', ProgrammingLanguageDialogController);

    ProgrammingLanguageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProgrammingLanguage', 'Snippet'];

    function ProgrammingLanguageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProgrammingLanguage, Snippet) {
        var vm = this;

        vm.programmingLanguage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.snippets = Snippet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.programmingLanguage.id !== null) {
                ProgrammingLanguage.update(vm.programmingLanguage, onSaveSuccess, onSaveError);
            } else {
                ProgrammingLanguage.save(vm.programmingLanguage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('codeSnipApp:programmingLanguageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
