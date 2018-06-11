angular.module('auth')
.config(route);

function route($routeProvider) {

    $routeProvider
    .when('/login', {
        templateUrl: '/auth/login.html',
        controllerAs: 'vm',
        controller: 'LoginController'
    });
}
