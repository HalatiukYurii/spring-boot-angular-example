angular
    .module('product-edit')
    .controller('ProductEditController', controller);

function controller(productService, $location, product) {
    var vm = this;

    vm.save = save;
    vm.errors = {};

    vm.product = product ? product : {};

    function save() {
        var promise;
        vm.errors = {};

        if (vm.product.id) {
            promise = productService.update(vm.product);
        } else {
            promise = productService.create(vm.product);
        }

        promise.then(function() {
            $location.path('/products');
        })
        .catch(function(response) {
            console.log(response);
            vm.errors = response.data;
        })
    }
}
