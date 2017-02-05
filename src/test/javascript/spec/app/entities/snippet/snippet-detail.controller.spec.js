'use strict';

describe('Controller Tests', function() {

    describe('Snippet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSnippet, MockComment, MockRating, MockUser, MockProgrammingLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSnippet = jasmine.createSpy('MockSnippet');
            MockComment = jasmine.createSpy('MockComment');
            MockRating = jasmine.createSpy('MockRating');
            MockUser = jasmine.createSpy('MockUser');
            MockProgrammingLanguage = jasmine.createSpy('MockProgrammingLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Snippet': MockSnippet,
                'Comment': MockComment,
                'Rating': MockRating,
                'User': MockUser,
                'ProgrammingLanguage': MockProgrammingLanguage
            };
            createController = function() {
                $injector.get('$controller')("SnippetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'codeSnipApp:snippetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
