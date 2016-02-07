'use strict';

angular.module('jhipstersampleApp')
	.controller('SettingDeleteController', function($scope, $uibModalInstance, entity, Setting) {

        $scope.setting = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Setting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
