var todoApp = angular.module('todoApp');

todoApp.factory('loginService', function($http, $location) {
	var loginPage = {};
	
	loginPage.loginuser = function(user) {
		return $http({
			method : "POST",
			url : 'login',
			data : user
		});
	}
	return loginPage;
});