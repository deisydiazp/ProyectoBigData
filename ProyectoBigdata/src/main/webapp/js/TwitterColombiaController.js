'use strict';

/* Controllers */
var module = angular.module('adminTwitterColombia.controllers', []);

module.controller('adminTwitterColombiaCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    
    $scope.filtroHashtag="";
    $scope.filtroPersonas="";
    $scope.filtroTopQuery=10;
    $scope.getTwitterDashboard = function () {
    
        var topics;
        var influencers;
        var limit;
        
        if($scope.filtroHashtag == null || $scope.filtroHashtag == "")
            topics="none";
        else
            topics=$scope.filtroHashtag;
        
        if($scope.filtroPersonas == null || $scope.filtroPersonas == "")
            influencers="none";
        else
            influencers=$scope.filtroPersonas;
        
        if($scope.filtroTopQuery == null || $scope.filtroTopQuery == "")
            limit=10;
        else
            limit=$scope.filtroTopQuery;
        
        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/TwitterColombia/GetTwitterDashboard/' + topics + '/' + influencers + '/' + limit, {})
            .success(function (data, status, headers, config) {
                GraphLine(data[0].graph_values, "Tweets", "Cantidad de Tweets por dia");
                GraphLine(data[1].graph_values, "Retweets", "Cantidad de Retweets por dia");
                GraphLine(data[2].graph_values, "Seguidores", "Cantidad de Seguidores");
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };
    
    
    $scope.listTopTopics = {};
    $scope.getTopTopics = function () {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/TwitterColombia/GetTopTopics', {})
            .success(function (data, status, headers, config) {
                //alert(data.length);
                $scope.listTopTopics = data;
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };
    
    $scope.listInfluencers = {};
    $scope.getInfluencers = function () {

        $scope.errores = {};
        var error = false;

        if (error)
            return;

        $http.get('./webresources/TwitterColombia/GetInfluencers', {})
            .success(function (data, status, headers, config) {
                //alert(data.length);
                $scope.listInfluencers = data;
            })
            .error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
    };
    
    $scope.getTopTopics();
    $scope.getInfluencers();
    
    
}]);
