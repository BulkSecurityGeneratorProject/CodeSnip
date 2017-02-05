(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .config(bootstrapMaterialDesignConfig);

    bootstrapMaterialDesignConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
