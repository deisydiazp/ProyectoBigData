'use strict';

/* Controllers */
var module = angular.module('adminTwitterColombia.controllers', []);

module.controller('adminTwitterColombiaCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    
    $scope.getTwitterDashboard = function () {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/TwitterColombia/GetTwitterDashboard', {})
            .success(function (data, status, headers, config) {
                GraphLine(data[0].graph_values, "Tweets", "Cantidad de Tweets por dia");
                GraphLine(data[1].graph_values, "Retweets", "Cantidad de Retweets por dia");
                GraphLine(data[2].graph_values, "Seguidores", "Cantidad de Seguidores");
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };
    
    
}]);
