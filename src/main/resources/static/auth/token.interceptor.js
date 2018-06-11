angular
    .module('auth')
    .factory('tokenInterceptor', tokenInterceptor)
    .config(config);

function config($httpProvider) {
    $httpProvider.interceptors.push('tokenInterceptor');
}

function tokenInterceptor($q, $injector) {

    var interceptor = {
            request: request,
            responseError: responseError
        };

    return interceptor;

    function request(config) {
        if ($injector.get('authService').getToken() && !isHtml(config.url)) {
            config.headers.Authorization = 'Bearer ' + $injector.get('authService').getToken();
        }
        return config;
    }

    function responseError(response) {
        if (response.status === 401) {
            $injector.get('authService').clearToken();
        } else if (response.status === 403) {
            alert('You have no rights to this resource!');
        }
        return $q.reject(response);
    }

    function isHtml(url) {
        return url.lastIndexOf('.html') === url.length - '.html'.length;
    }
}