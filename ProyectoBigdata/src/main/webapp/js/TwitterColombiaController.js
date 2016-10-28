'use strict';

/* Controllers */
var module = angular.module('adminTwitterColombia.controllers', []);

module.controller('adminTwitterColombiaCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    
    $scope.getTwitterDashboard = function () {

        alert('ffff');
        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/TwitterColombia/GetTwitterDashboard', {})
            .success(function (data, status, headers, config) {
                alert('ya');
                //GraphDatasetAnnoted(data, type);
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };
    
}]);
