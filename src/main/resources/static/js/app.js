var app = angular.module('app', [
  'ngRoute',
  'ngResource'
]);

app.config(function($routeProvider) {

    $routeProvider
        .when('/products', {
            templateUrl: '/views/product-list.html',
            controllerAs: 'controller',
            controller: 'ProductListController'
        })
        .when('/addProduct', {
            templateUrl: '/views/product-form.html',
            controllerAs: 'controller',
            controller: 'ProductFormController'
        })
        .when('/users', {
            templateUrl: '/views/user-list.html',
            controller: 'UserListController'
        })
        .otherwise({
            redirectTo: '/'
        });
});

