var todoApp = angular.module('todoApp');

todoApp.factory('registerService', function($http) {
	var register = {};
	
	register.registeruser = function(user) {
		console.log("User details : "+user.email);
		return $http({
			method : "POST",
			url : 'registrationForm',
			data : user
		});
	}
	return register;
});