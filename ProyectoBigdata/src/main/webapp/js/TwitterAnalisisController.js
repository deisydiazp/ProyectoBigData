'use strict';

/* Controllers */
var module = angular.module('adminTwitter.controllers', []);

module.controller('adminTwitterCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    
    $scope.filtroDataset = 1;
    $scope.cambiarDataset = function () {
        var numDataset =$scope.filtroDataset;
        $scope.obtenerDatasetsAnotados(numDataset,"Annoted");
        $scope.obtenerDatasetsAnotados(numDataset,"Assigned");
        //$scope.obtenerTemasDatasets(numDataset,"Tags");
        //$scope.obtenerTemasDatasets(numDataset,"Person");
        $scope.obtenerUsuariosDatasets(numDataset,"graphBarHorizontal");
    }    
        
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
    
    $scope.obtenerUsuariosDatasets = function (numDataset, type) {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/Twitter/UsuariosDatasetsAnotados/'+ numDataset + '/' + type, {})
            .success(function (data, status, headers, config) {
                GraphBarHorizontal(data,type);
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };

    $scope.obtenerDatasetsAnotados(1,"Annoted");
    $scope.obtenerDatasetsAnotados(1,"Assigned");
    $scope.obtenerTemasDatasets(1,"Tags");
    $scope.obtenerTemasDatasets(1,"Person");
    $scope.obtenerUsuariosDatasets(1,"graphBarHorizontal");
    
}]);
