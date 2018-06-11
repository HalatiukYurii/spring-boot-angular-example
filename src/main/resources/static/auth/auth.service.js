angular.module('auth')
.service('authService', authService);

function authService($http, $location, SERVER_URL) {
    var service = this,
        token;

    service.login = login;
    service.logout = logout;
    service.getToken = getToken;
    service.clearToken = clearToken;

    function getToken() {
        if (!token) {
            redirectToLoginPage();
        }
        return token;
    }

    function clearToken() {
        token = undefined;
        redirectToLoginPage();
    }

    function login(username, password) {
        return $http({
            method: 'POST',
            url: SERVER_URL + '/oauth/token?grant_type=password',
            data: 'username=' + username + '&password=' + password,
            headers: {
                'Authorization': 'Basic ' + btoa('user-client:planIt'),
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
        .then(function(response) {
            token = response.data.access_token;
        });
    }

    function logout() {
        return $http({
            method: 'POST',
            url: SERVER_URL + '/users/logout'
        })
        .then(function() {
            clearToken();
        });
    }

    function redirectToLoginPage() {
        $location.path('/login');
    }
}