'use strict';

angular.module('jhipstersampleApp')
    .controller('MainController', function ($scope, Principal, Point) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        Point.thisWeek(function(data) {
            $scope.pointsThisWeek = data;
            $scope.pointsPercentage = (data.points / 21) * 100;
        });
    });
