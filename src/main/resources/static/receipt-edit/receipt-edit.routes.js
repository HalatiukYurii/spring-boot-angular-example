angular
    .module('receipt-edit')
    .config(route);

function route($routeProvider) {

    $routeProvider
    .when('/receipts/edit/:id', {
        templateUrl: '/receipt-edit/receipt-edit.html',
        controllerAs: 'vm',
        controller: 'ReceiptEditController',
        resolve: {
            receipt: function(receiptService, $route) {
                return receiptService.get($route.current.params.id);
            },
            products: function(productService) {
                return productService.query()
                .then(function(response) {
                    return response.content;
                });
            }
        }
    })
    .when('/receipts/add', {
        templateUrl: '/receipt-edit/receipt-edit.html',
        controllerAs: 'vm',
        controller: 'ReceiptEditController',
        resolve: {
            receipt: function() {
                return undefined;
            },
            products: function(productService) {
                return productService.query()
                .then(function(response) {
                    return response.content;
                });
            }
        }
    });
}
