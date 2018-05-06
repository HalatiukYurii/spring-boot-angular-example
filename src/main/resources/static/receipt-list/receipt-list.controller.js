angular
    .module('receipt-list')
    .controller('ReceiptListController', controller);

function controller(receipts, $routeParams, $route, $location) {
    var vm = this;

    vm.search = search;
    vm.editReceipt = editReceipt;

    vm.params = $routeParams;
    vm.receipts = receipts;

    function search() {
        $route.updateParams(vm.params);
        $route.reload();
    }

    function editReceipt(receipt) {
        console.log('/receipts/edit/' + receipt.id);
        $location.path('/receipts/edit/' + receipt.id);
    }
}
