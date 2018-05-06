angular
    .module('receipt')
    .service('receiptService', service);

function service($resource, SERVER_URL) {

    this.get = get;
    this.query = query;
    this.create = create;
    this.update = update;
    this.remove = remove;

    var receiptResource = $resource(SERVER_URL + '/receipts/:id', {}, {
        query: {
            method: 'GET',
            isArray: false,
            url: SERVER_URL + '/receipts'
        },
        update: {
            method: 'PUT'
        }
    });

    function get(id) {
        return receiptResource.get({
            'id': id
        }).$promise
        .then(function(receipt) {
            receipt.date = new Date(receipt.date + 'Z');
            return receipt;
        });
    }

    function query(params) {
        return receiptResource.query(params).$promise
        .then(function(page) {
            page.content.forEach(function(receipt) {
                receipt.date = new Date(receipt.date + 'Z');
            });
            return page;
        });
    }

    function create(receipt) {
        return receiptResource.save(null, parseReceiptDate(receipt)).$promise;
    }

    function update(receipt) {
        return receiptResource.update({
            'id': receipt.id
        }, parseReceiptDate(receipt)).$promise;
    }

    function remove(id) {
        return receiptResource.remove({
            'id': id
        }).$promise;
    }

    function parseReceiptDate(receipt) {
        var newReceipt = angular.copy(receipt),
            isoDate = receipt.date.toISOString();

        newReceipt.date = isoDate;
        return newReceipt;
    }
}