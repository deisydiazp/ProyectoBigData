'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminTwitter', [
  'ngRoute',
  'adminTwitter.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/Twitter.html', controller: 'adminTwitterCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
