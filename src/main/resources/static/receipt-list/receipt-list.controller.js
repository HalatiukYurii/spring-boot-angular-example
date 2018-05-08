angular
    .module('receipt-list')
    .controller('ReceiptListController', controller);

function controller(receipts, $routeParams, $route, $location) {
    var vm = this;

    vm.search = search;
    vm.editReceipt = editReceipt;
    vm.editProduct = editProduct;

    vm.params = $routeParams;
    vm.receipts = receipts;

    function search() {
        $route.updateParams(vm.params);
        $route.reload();
    }

    function editReceipt(receipt) {
        $location.path('/receipts/edit/' + receipt.id);
    }

    function editProduct(product) {
        $location.path('/products/edit/' + product.id);
    }
}
