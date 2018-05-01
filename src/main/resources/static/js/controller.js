app.controller('ProductListController', function($resource) {
    var controller = this;
    controller.headingTitle = 'Product List';

    controller.params = {};

    var productResource = $resource('http://localhost:8081/products', {}, {
        query: {
            method: 'GET',
            isArray: false
        }
    });

    controller.search = function() {
        productResource.query(controller.params).$promise
        .then(function(response) {
            controller.products = response.content;
        });
    };

    controller.search();
});

app.controller('ProductFormController', function($resource) {
    var controller = this;

    controller.product = {
        "name": "ala"
    };

    var productResource = $resource(
    "http://localhost:8081/products");

    controller.add = function() {
        productResource.save(controller.product);
    }
});


app.controller('UserListController', function($scope) {
    $scope.headingTitle = "User List";
});