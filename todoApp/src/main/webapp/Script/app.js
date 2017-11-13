var todoApp = angular.module('todoApp', [ 'ui.router']);

todoApp.config(function($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('registrationForm', {
		url : '/registrationForm',
		templateUrl : 'template/registration.html',
		controller : 'registerController'
	}).state('login', {
		url : '/login',
		templateUrl : 'template/login.html',
		controller : 'loginController'
	});
	$urlRouterProvider.otherwise('login');
});
