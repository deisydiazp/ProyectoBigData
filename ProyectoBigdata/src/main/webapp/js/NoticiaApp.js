'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminNoticia', [
  'ngRoute',
  'angularUtils.directives.dirPagination',
  'adminNoticia.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/Noticia.html', controller: 'NoticiaCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
