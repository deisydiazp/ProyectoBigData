'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminEvento', [
  'ngRoute',
  'adminEvento.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/Evento.html', controller: 'EventoCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
