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
                GraphLine(data[0].graph_values, "1", "Tweets de temas en el Tiempo");
                GraphLine(data[1].graph_values, "2", "Retweets de temas en el Tiempo");
                GraphLine(data[2].graph_values, "3", "Seguidores de temas en el Tiempo");
                GraphLine(data[3].graph_values, "4", "Tweets de usuarios en el Tiempo");
                GraphLine(data[4].graph_values, "5", "Retweets de usuarios en el Tiempo");
                GraphLine(data[5].graph_values, "6", "Seguidores de usuarios en el Tiempo");
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
