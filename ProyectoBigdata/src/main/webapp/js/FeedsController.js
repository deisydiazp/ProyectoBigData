'use strict';

/* Controllers */
var module = angular.module('adminFeeds.controllers', []);

module.controller('FeedsCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
        //listar
        $scope.lista = {};
        $scope.datosFormulario = {
            
        };
        
        $scope.panelEditar = false;

        $scope.listar = function () {
            $http.get('./webresources/Feeds', {})
                    .success(function (data, status, headers, config) {
                        $scope.lista = data;
                    }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        $scope.listar();

        //filtrar
        $scope.filtrarXQuery = function () {
            $scope.errores = {};
            var error = false;

            if (error)
                return;
            
            $scope.datosFormulario.categoria=$('input[name=categoria][checked=checked]').val();
            $scope.datosFormulario.filtroFeed=$('input[name=filtro][checked=checked]').val();
            $scope.datosFormulario.excluyente=$('#excluyente').is(":checked");
            //$scope.datosFormulario.metodo='Xquery';
            
            $http.post('./webresources/Feeds/filtro', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                $scope.lista = data;
            }).error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
        };
        
        //filtrar
        $scope.filtrarRegex = function () {
            $scope.errores = {};
            var error = false;

            if (error)
                return;
            
            $scope.datosFormulario.categoria=$('input[name=categoria][checked=checked]').val();
            $scope.datosFormulario.filtroFeed=$('input[name=filtro][checked=checked]').val();
            $scope.datosFormulario.excluyente=$('#excluyente').is(":checked");
            //$scope.datosFormulario.metodo='Regex';
            
            $http.post('./webresources/Feeds/filtro', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                $scope.lista = data;
            }).error(function (data, status, headers, config) {
                alert('Error al filtrar datos, por favor intente m\xe1s tarde');
            });
        };


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
            $http.post('./webresources/Feeds', JSON.stringify($scope.datosFormulario), {}
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
        $scope.eliminar = function (data) {
            if (confirm('�Desea elminar este registro?')) {
                $http.delete('./webresources/Feeds/' + data.id, {})
                        .success(function (data, status, headers, config) {
                            $scope.listar();
                        }).error(function (data, status, headers, config) {
                    alert('Error al eliminar la informaci\xf3n de Feeds, por favor intente m\xe1s tarde');
                });
            }
        };
    }]);
