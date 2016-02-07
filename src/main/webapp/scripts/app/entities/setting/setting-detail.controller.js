'use strict';

angular.module('jhipstersampleApp')
    .controller('SettingDetailController', function ($scope, $rootScope, $stateParams, entity, Setting, User) {
        $scope.setting = entity;
        $scope.load = function (id) {
            Setting.get({id: id}, function(result) {
                $scope.setting = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstersampleApp:settingUpdate', function(event, result) {
            $scope.setting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
