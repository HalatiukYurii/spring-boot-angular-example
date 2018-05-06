angular
    .module('receipt-list')
    .config(route);

function route($routeProvider) {

    $routeProvider
    .when('/receipts', {
        templateUrl: '/receipt-list/receipt-list.html',
        controllerAs: 'vm',
        controller: 'ReceiptListController',
        resolve: {
            receipts: function($routeParams, receiptService) {
                return receiptService.query($routeParams)
                .then(function(response) {
                    return response.content;
                });
            }
        }
    });
}
