'use strict';

angular.module('jhipstersampleApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setting', {
                parent: 'entity',
                url: '/settings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstersampleApp.setting.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setting/settings.html',
                        controller: 'SettingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('setting');
                        $translatePartialLoader.addPart('weightUnit');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('setting.detail', {
                parent: 'entity',
                url: '/setting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipstersampleApp.setting.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setting/setting-detail.html',
                        controller: 'SettingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('setting');
                        $translatePartialLoader.addPart('weightUnit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Setting', function($stateParams, Setting) {
                        return Setting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setting.new', {
                parent: 'setting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setting/setting-dialog.html',
                        controller: 'SettingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    weeklyGoal: null,
                                    weightUnit: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('setting', null, { reload: true });
                    }, function() {
                        $state.go('setting');
                    })
                }]
            })
            .state('setting.edit', {
                parent: 'setting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setting/setting-dialog.html',
                        controller: 'SettingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Setting', function(Setting) {
                                return Setting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('setting.delete', {
                parent: 'setting',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setting/setting-delete-dialog.html',
                        controller: 'SettingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Setting', function(Setting) {
                                return Setting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
