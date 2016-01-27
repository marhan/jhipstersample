 'use strict';

angular.module('jhipstersampleApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jhipstersampleApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jhipstersampleApp-params')});
                }
                return response;
            }
        };
    });
