'use strict';

/* Controllers */
var module = angular.module('adminNodosWiki.controllers', []);

module.controller('adminNodosWikiCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    
    $scope.filtroFechaInicial=null;
    $scope.filtroFechaFinal=null;
    $scope.filtroPais=null;
    $scope.filtroNombre=null;
    
    $scope.filtrarParametros = function () {
            $scope.errores = {};
            var error = false;
            
            var fechaInicial = $scope.filtroFechaInicial.toISOString();
            var fechaFinal = $scope.filtroFechaFinal.toISOString();
            var filtroPais = $scope.filtroPais;
            var filtroNombre = $scope.filtroNombre;
         
            if(filtroPais == null ||  filtroPais== '')
                filtroPais = 'seleccionar'
            
            if( filtroNombre == null ||  filtroNombre == '')
                filtroNombre = 'ninguno'
         
            if (error)
                return;
            
            $http.get('./webresources/NodoWiki/filtro/'+fechaInicial+'/'+fechaFinal+'/'+filtroPais+'/'+filtroNombre,{})
            .success(function (data, status, headers, config) {
                if(data == "")
                    alert("NO SE ENCONTRARON DATOS CON LOS PARAMETROS SOLICITADOS");
                else
                    D3ok(data);
            }).error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
        };
    }]);
