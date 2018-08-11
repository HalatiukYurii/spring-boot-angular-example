angular.module('auth')
.service('userService', function(SERVER_URL, $resource) {

  var service = this;
  var userResource = $resource(SERVER_URL + '/users/:id');

  service.create = function(user) {
    return userResource.save(null, user).$promise;
  }
});