angular
    .module('product-edit')
    .controller('ProductEditController', controller);

function controller(productService, $location, product) {
    var vm = this;

    vm.save = save;
    vm.errors = {};

    vm.product = product ? product : {};

    function save() {
        vm.errors = {};

        if (vm.product.id) {
            productService.update(vm.product)
            .then(function() {
                $location.path('/products');
            })
            .catch(function(response) {
                vm.errors = response.data;
            });
        } else {
            productService.create(vm.product)
            .then(function() {
                $location.path('/products');
            })
            .catch(function(response) {
                vm.errors = response.data;
            });
        }
    }
}
