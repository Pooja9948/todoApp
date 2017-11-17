var todoApp = angular.module('todoApp');

todoApp.factory('loginService', function($http, $location) {
	var loginPage = {};
	
	loginPage.loginuser = function(user) {
		console.log(user.email);
		return $http({
			method : "POST",
			url : 'login',
			data : user
		});
	}
	return loginPage;
});