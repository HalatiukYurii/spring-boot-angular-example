var app = angular.module('app', [
    'ngRoute',
    'product-list',
    'product-edit',
    'receipt-list',
    'receipt-edit'
]);

app.config(function($routeProvider) {
    $routeProvider
    .otherwise({
        redirectTo: '/'
    });
});

