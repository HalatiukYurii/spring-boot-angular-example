angular.module('auth')
.controller('SignUpController', function(userService, $location) {

  var vm = this;

  vm.user = {
    role: 'USER'
  };

  vm.signUp = signUp;

  function signUp() {
    if (vm.passwordConfirm && vm.passwordConfirm === vm.user.password) {
      userService.create(vm.user)
        .then(function() {
          $location.path('/login');
        })
        .catch(function(response) {
          vm.errors = response.data;
        });
    } else {
      vm.errors = {
        passwordConfirm: 'Password Confirmation does not match!'
      }
    }
  }
});