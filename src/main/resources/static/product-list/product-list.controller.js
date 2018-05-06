angular
    .module('product-list')
    .controller('ProductListController', controller);

function controller(products, $routeParams, $route, $location) {
    var vm = this;

    vm.search = search;
    vm.editProduct = editProduct;

    vm.params = $routeParams;
    vm.products = products;

    function search() {
        $route.updateParams(vm.params);
        $route.reload();
    }

    function editProduct(product) {
        $location.path('/products/edit/' + product.id);
    }
}
