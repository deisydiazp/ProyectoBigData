'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminNodosWiki', [
  'ngRoute',
  'adminNodosWiki.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/NodosWiki.html', controller: 'adminNodosWikiCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
