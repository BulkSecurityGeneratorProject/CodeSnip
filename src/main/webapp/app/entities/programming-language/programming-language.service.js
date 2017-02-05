(function() {
    'use strict';
    angular
        .module('codeSnipApp')
        .factory('ProgrammingLanguage', ProgrammingLanguage);

    ProgrammingLanguage.$inject = ['$resource'];

    function ProgrammingLanguage ($resource) {
        var resourceUrl =  'api/programming-languages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
