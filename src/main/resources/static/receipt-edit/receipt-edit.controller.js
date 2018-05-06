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

    function saveReceipt() {
        if (vm.receipt.id) {
            receiptService.update(vm.receipt)
            .then(function() {
                $location.path('/receipts');
            });
        } else {
            receiptService.create(vm.receipt)
            .then(function() {
                $location.path('/receipts');
            });
        }
    }

    function addProduct() {
        vm.receipt.products.push(vm.selectedProduct);
    }
}
