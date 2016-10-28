'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminTwitterColombia', [
  'ngRoute',
  'adminTwitterColombia.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/TwitterColombia.html', controller: 'adminTwitterColombiaCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
