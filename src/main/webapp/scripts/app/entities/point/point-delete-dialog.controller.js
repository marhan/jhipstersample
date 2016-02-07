'use strict';

angular.module('jhipstersampleApp')
	.controller('PointDeleteController', function($scope, $uibModalInstance, entity, Point) {

        $scope.point = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Point.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
