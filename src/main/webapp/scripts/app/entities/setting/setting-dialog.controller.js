'use strict';

angular.module('jhipstersampleApp').controller('SettingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Setting', 'User',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Setting, User) {

        $scope.setting = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Setting.get({id : id}, function(result) {
                $scope.setting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstersampleApp:settingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.setting.id != null) {
                Setting.update($scope.setting, onSaveSuccess, onSaveError);
            } else {
                Setting.save($scope.setting, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
