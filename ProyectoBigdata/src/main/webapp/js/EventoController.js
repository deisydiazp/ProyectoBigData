'use strict';

/* Controllers */
var module = angular.module('adminEvento.controllers', []);

module.controller('EventoCtrl', ['$scope', '$filter', '$http','$location', function ($scope, $filter, $http, $location) {
        
    console.log($location.search().ua);
    //listar
    $scope.lista = {};
    $scope.datosFormulario = {};
    $scope.panelEditar = false;
    $scope.listar=function(){
        $http.get('./webresources/Evento', {})
            .success(function (data, status, headers, config) {
                $scope.lista = data;
            }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
        });    
    };

    $scope.listarUnidadAcademica=function(){
        $http.get('./webresources/UnidadAcademica', {})
            .success(function (data, status, headers, config) {
                $scope.listaUnidadAcademica = data;
            }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n de unidadAcademica, por favor intente m\xe1s tarde');
        });    
    };
    $scope.listarUnidadAcademica();
        

    $scope.listar();
    //guardar
    $scope.nuevo = function () {
        $scope.panelEditar = true;
        $scope.datosFormulario = {};
    };
    
    $scope.guardar = function () {
        $scope.errores = {};
        var error = false;
        
        if (error)
            return;
        $http.post('./webresources/Evento', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                alert("Los datos han sido guardados con Exito");
                $scope.panelEditar = false;
                $scope.listar();
            }).error(function (data, status, headers, config) {
                alert('Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
    };
    $scope.cancelar = function () {
        $scope.panelEditar = false;
        $scope.datosFormulario = {};
    };

    //editar
    $scope.editar = function (data) {
        $scope.panelEditar = true;
        $scope.datosFormulario = data;
    };
    //eliminar
    $scope.eliminar = function (data){
        if (confirm('�Desea elminar este registro?')) {    
            $http.delete('./webresources/Evento/'+data.id, {})
                .success(function (data, status, headers, config) {
                    $scope.listar();
                }).error(function (data, status, headers, config) {    
                    alert('Error al eliminar la informaci\xf3n de Evento, por favor intente m\xe1s tarde');
            });   
        }
    };
    
    
    $scope.listarEventos=function(ua){
        $http.get('./webresources/Evento/unidadAcademica/'+ua, {})
            .success(function (data, status, headers, config) {
                $scope.lista = data;
            }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
        });    
    };
    $scope.listarEventos($location.search().ua);
    
}]);
