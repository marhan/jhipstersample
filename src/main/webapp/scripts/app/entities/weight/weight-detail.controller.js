'use strict';

angular.module('jhipstersampleApp')
    .controller('WeightDetailController', function ($scope, $rootScope, $stateParams, entity, Weight, User) {
        $scope.weight = entity;
        $scope.load = function (id) {
            Weight.get({id: id}, function(result) {
                $scope.weight = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstersampleApp:weightUpdate', function(event, result) {
            $scope.weight = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
