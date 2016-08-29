'use strict';


// Declare app level module which depends on filters, and services
var module=angular.module('adminFeeds', [
  'ngRoute',
  'adminFeeds.controllers'
]);
module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {templateUrl: 'partials/Feeds.html', controller: 'FeedsCtrl'});
  $routeProvider.otherwise({redirectTo: '/'}); 
}]);
