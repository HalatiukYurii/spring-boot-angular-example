var app =

angular.module('app', [
  'ngRoute',
  'ngResource'
]);

app.config(function($routeProvider) {

    $routeProvider
        .when('/products', {
            templateUrl: '/views/product-list.html',
            controller: 'ProductListController'
        })
        .when('/users', {
            templateUrl: '/views/user-list.html',
            controller: 'UserListController'
        })
        .otherwise({
            redirectTo: '/'
        });
});

