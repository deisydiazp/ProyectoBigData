'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminUnidadAcademica', [
  'ngRoute',
  'adminUnidadAcademica.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/UnidadAcademica.html', controller: 'UnidadAcademicaCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
