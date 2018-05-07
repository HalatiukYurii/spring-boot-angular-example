angular
    .module('product-edit')
    .controller('ProductEditController', controller);

function controller(productService, $location, product) {
    var vm = this;

    vm.save = save;

    vm.product = product ? product : {};

    function save() {
        if (vm.product.id) {
            productService.update(vm.product)
            .then(function() {
                $location.path('/products');
            });
        } else {
            productService.create(vm.product)
            .then(function() {
                $location.path('/products');
            });
        }
    }
}
