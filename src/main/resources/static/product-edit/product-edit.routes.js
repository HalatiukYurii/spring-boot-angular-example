angular
    .module('product-edit')
    .config(route);

function route($routeProvider) {

    $routeProvider
    .when('/products/add', {
        templateUrl: '/product-edit/product-edit.html',
        controllerAs: 'vm',
        controller: 'ProductEditController',
        resolve: {
            product: function() {
                return undefined;
            }
        }
    })
    .when('/products/edit/:id', {
        templateUrl: '/product-edit/product-edit.html',
        controllerAs: 'vm',
        controller: 'ProductEditController',
        resolve: {
            product: function(productService, $route) {
                return productService.get($route.current.params.id);
            }
        }
    });
}
