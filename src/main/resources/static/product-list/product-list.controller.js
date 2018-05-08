angular
    .module('product-list')
    .controller('ProductListController', controller);

function controller(productPage, $routeParams, $route, $location) {
    var vm = this;

    vm.search = search;
    vm.editProduct = editProduct;
    vm.changePage = changePage;

    vm.params = $routeParams;
    vm.products = productPage.content;

    function search() {
        $route.updateParams(vm.params);
        $route.reload();
    }

    function editProduct(product) {
        $location.path('/products/edit/' + product.id);
    }

    function changePage(isPrevious) {
        var newPage = productPage.number;
        if (isPrevious && !productPage.first) {
            newPage--;
        } else if (!isPrevious && !productPage.last) {
            newPage++;
        }
        vm.params.page = newPage;
        search();
    }
}
