(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('UserProfileDetailController', UserProfileDetailController);

    UserProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UserProfile', 'User'];

    function UserProfileDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UserProfile, User) {
        var vm = this;

        vm.userProfile = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('codeSnipApp:userProfileUpdate', function(event, result) {
            vm.userProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
