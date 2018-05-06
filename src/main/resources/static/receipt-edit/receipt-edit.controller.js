angular
    .module('receipt-edit')
    .controller('ReceiptEditController', controller);

function controller(receiptService, $location, products, receipt, $routeParams) {
    var vm = this;

    vm.saveReceipt = saveReceipt;
    vm.addProduct = addProduct;

    vm.receipt = receipt ? receipt : {
        products: []
    };
    vm.products = products;
    vm.errors = {};

    function saveReceipt() {
        var promise;
        vm.errors = {};

        if (vm.receipt.id) {
            promise = receiptService.update(vm.receipt)
        } else {
            promise = receiptService.create(vm.receipt)
        }

        promise.then(function() {
            $location.path('/receipts');
        })
        .catch(function(response) {
            vm.errors = response.data;
        });
    }

    function addProduct() {
        vm.receipt.products.push(vm.selectedProduct);
    }
}
