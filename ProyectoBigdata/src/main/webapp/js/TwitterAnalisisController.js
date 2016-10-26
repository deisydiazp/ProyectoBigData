'use strict';

/* Controllers */
var module = angular.module('adminTwitter.controllers', []);

module.controller('adminTwitterCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    $scope.obtenerDatasetsAnotados = function (numDataset, type) {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/Twitter/DatasetsAnotados/'+ numDataset + '/' + type, {})
            .success(function (data, status, headers, config) {
                GraphDatasetAnnoted(data, type);
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };

     $scope.obtenerTemasDatasets = function (numDataset, type) {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/Twitter/TemasDatasetsAnotados/'+ numDataset + '/' + type, {})
            .success(function (data, status, headers, config) {
                GraphWordCloud(data, type);
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };

    $scope.obtenerDatasetsAnotados(1,"Annoted");
    $scope.obtenerDatasetsAnotados(1,"Assigned");
    $scope.obtenerTemasDatasets(1,"Tags");
    $scope.obtenerTemasDatasets(1,"Person");
}]);
