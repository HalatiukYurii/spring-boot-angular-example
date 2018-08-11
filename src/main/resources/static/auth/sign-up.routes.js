angular.module('auth')
.config(function($routeProvider) {
  $routeProvider.when('/signUp', {
    templateUrl: '/auth/sign-up.html',
    controller: 'SignUpController',
    controllerAs: 'vm'
  });
});