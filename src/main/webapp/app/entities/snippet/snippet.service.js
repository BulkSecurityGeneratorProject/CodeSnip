(function() {
    'use strict';
    angular
        .module('codeSnipApp')
        .factory('Snippet', Snippet);

    Snippet.$inject = ['$resource'];

    function Snippet ($resource) {
        var resourceUrl =  'api/snippets/:id';

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
