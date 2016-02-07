'use strict';

angular.module('jhipstersampleApp')
	.controller('WeightDeleteController', function($scope, $uibModalInstance, entity, Weight) {

        $scope.weight = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Weight.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
