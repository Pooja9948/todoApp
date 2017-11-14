var todoApp = angular.module('todoApp');

todoApp.factory('resetpasswordService', function($http, $location) {
	var reset = {};

	reset.resetPassword = function(user) {
		console.log(user);
		return $http({
			method : "PUT",
			url : 'resetpassword',
			data : user
		})
	}
	return reset;
});