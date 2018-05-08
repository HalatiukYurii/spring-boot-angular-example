angular
    .module('product-list')
    .config(route);

function route($routeProvider) {

    $routeProvider
    .when('/products', {
        templateUrl: '/product-list/product-list.html',
        controllerAs: 'vm',
        controller: 'ProductListController',
        resolve: {
            productPage: function($routeParams, productService) {
                return productService.query($routeParams);
            }
        }
    });
}
