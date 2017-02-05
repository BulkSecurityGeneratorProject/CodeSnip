(function () {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(bootstrapMaterialDesignConfig);

    bootstrapMaterialDesignConfig.$inject = ['hljsServiceProvider'];

    function bootstrapMaterialDesignConfig(hljsServiceProvider) {
        hljsServiceProvider.setOptions({
            // replace tab with 4 spaces
            tabReplace: '    '
        });
    }

})();
