'use strict';

angular.module('jhipstersampleApp')
    .factory('Setting', function ($resource, DateUtils) {
        return $resource('api/settings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
