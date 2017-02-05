(function () {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(highlighConfig);

    highlighConfig.$inject = ['hljsServiceProvider'];

    function highlighConfig(hljsServiceProvider) {
        hljsServiceProvider.setOptions({
            // replace tab with 4 spaces
            tabReplace: '    '
        });
    }

})();
