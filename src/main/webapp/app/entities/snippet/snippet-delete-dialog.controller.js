(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('SnippetDeleteController',SnippetDeleteController);

    SnippetDeleteController.$inject = ['$uibModalInstance', 'entity', 'Snippet'];

    function SnippetDeleteController($uibModalInstance, entity, Snippet) {
        var vm = this;

        vm.snippet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Snippet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
