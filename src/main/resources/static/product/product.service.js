angular
    .module('product')
    .service('productService', service);

function service($resource, SERVER_URL) {

    this.get = get;
    this.query = query;
    this.create = create;
    this.update = update;
    this.remove = remove;

    var productResource = $resource(SERVER_URL + '/products/:id', {}, {
        query: {
            method: 'GET',
            isArray: false,
            url: SERVER_URL + '/products'
        },
        update: {
            method: 'PUT'
        }
    });

    function get(id) {
        return productResource.get({
            'id': id
        }).$promise;
    }

    function query(params) {
        return productResource.query(params).$promise;
    }

    function create(product) {
        return productResource.save(null, product).$promise;
    }

    function update(product) {
        return productResource.update({
            'id': product.id
        }, product).$promise;
    }

    function remove(id) {
        return productResource.remove({
            'id': id
        }).$promise;
    }
}