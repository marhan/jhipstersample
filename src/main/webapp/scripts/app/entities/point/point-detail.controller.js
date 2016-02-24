'use strict';

angular.module('jhipstersampleApp')
    .controller('PointDetailController', function ($scope, $rootScope, $stateParams, entity, Point, User) {
        $scope.point = entity;
        $scope.load = function (id) {
            Point.get({id: id}, function(result) {
                $scope.point = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstersampleApp:pointUpdate', function(event, result) {
            $scope.point = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
