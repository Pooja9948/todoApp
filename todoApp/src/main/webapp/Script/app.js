var todoApp = angular.module('todoApp', [ 'ui.router', 'ngSanitize',
		'ui.bootstrap', 'toastr', 'ui.bootstrap.datepicker' ]);

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
	}).state('home', {
		url : '/home',
		templateUrl : 'template/home.html',
		controller : 'homeController'
	}).state('archive', {
		url : '/archive',
		templateUrl : 'template/archive.html',
		controller : 'homeController'
	}).state('trash', {
		url : '/trash',
		templateUrl : 'template/trash.html',
		controller : 'homeController'
	}).state('reminder', {
		url : '/reminder',
		templateUrl : 'template/reminder.html',
		controller : 'homeController'
	}).state('dummy', {
		url : '/dummy',
		controller : 'dummyController'
	});
	$urlRouterProvider.otherwise('login');
});
