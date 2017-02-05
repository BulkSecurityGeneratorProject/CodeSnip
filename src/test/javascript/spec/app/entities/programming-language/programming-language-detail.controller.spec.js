'use strict';

describe('Controller Tests', function() {

    describe('ProgrammingLanguage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProgrammingLanguage, MockSnippet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProgrammingLanguage = jasmine.createSpy('MockProgrammingLanguage');
            MockSnippet = jasmine.createSpy('MockSnippet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProgrammingLanguage': MockProgrammingLanguage,
                'Snippet': MockSnippet
            };
            createController = function() {
                $injector.get('$controller')("ProgrammingLanguageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'codeSnipApp:programmingLanguageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
