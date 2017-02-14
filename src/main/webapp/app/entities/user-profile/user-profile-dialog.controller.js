(function() {
    'use strict';

    angular
        .module('codeSnipApp')
        .controller('UserProfileDialogController', UserProfileDialogController);

    UserProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$q', 'DataUtils', 'entity', 'UserProfile', 'User'];

    function UserProfileDialogController ($timeout, $scope, $stateParams, $q, DataUtils, entity, UserProfile, User) {
        var vm = this;

        vm.userProfile = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
        }

        function save () {
            vm.isSaving = true;
            if (vm.userProfile.id !== null) {
                UserProfile.update(vm.userProfile, onSaveSuccess, onSaveError);
            } else {
                UserProfile.save(vm.userProfile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('codeSnipApp:userProfileUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setProfileImage = function ($file, userProfile) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        userProfile.profileImage = base64Data;
                        userProfile.profileImageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
