'use strict';

angular.module('jhipstersampleApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


