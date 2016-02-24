'use strict';

angular.module('jhipstersampleApp')
    .controller('SettingController', function ($scope, $state, Setting) {

        $scope.settings = [];
        $scope.loadAll = function() {
            Setting.query(function(result) {
               $scope.settings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.setting = {
                weeklyGoal: null,
                weightUnit: null,
                id: null
            };
        };
    });
