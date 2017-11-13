var todoApp = angular.module('todoApp');

todoApp.factory('registerService', function($http, $location) {
	var register = {};
	
	register.registeruser = function(user) {
		return $http({
			method : "POST",
			url : 'registrationForm',
			data : user
		});
	}
	return register;
});