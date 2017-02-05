(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('ProgrammingLanguageDeleteController',ProgrammingLanguageDeleteController);

    ProgrammingLanguageDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProgrammingLanguage'];

    function ProgrammingLanguageDeleteController($uibModalInstance, entity, ProgrammingLanguage) {
        var vm = this;

        vm.programmingLanguage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProgrammingLanguage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
