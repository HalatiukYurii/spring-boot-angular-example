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
            products: function($routeParams, productService) {
                console.log($routeParams);
                return productService.query($routeParams)
                .then(function(response) {
                    return response.content;
                });
            }
        }
    });
}
