angular.module('auth')
.controller('LoginController', controller);

function controller(authService, $location) {
    var vm = this;

    vm.login = login;

    function login() {
        authService.login(vm.username, vm.password)
        .then(function() {
            $location.path('/');
        });
    }
}