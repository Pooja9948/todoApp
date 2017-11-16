var todoApp = angular.module('todoApp', [ 'ui.router']);

todoApp.config(function($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('registrationForm', {
		url : '/registrationForm',
		templateUrl : 'template/registerpage.html',
		controller : 'registerController'
	}).state('login', {
		url : '/login',
		templateUrl : 'template/loginpage.html',
		controller : 'loginController'
	}).state('forgotpassword', {
		url : '/forgotpassword',
		templateUrl : 'template/forgetpassword.html',
		controller : 'forgotpasswordController'
	}).state('resetpassword', {
		url : '/resetpassword',
		templateUrl : 'template/resetpassword.html',
		controller : 'resetpasswordController'
	}).state('otp', {
		url : '/otp',
		templateUrl : 'template/otp.html',
		controller : 'otpController'
	});
	$urlRouterProvider.otherwise('login');
});
