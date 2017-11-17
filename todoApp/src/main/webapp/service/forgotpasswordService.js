var todoApp = angular.module('todoApp');

todoApp.factory('forgotpasswordService', function($http, $location) {
	var reset = {};

	reset.sendLinkToEmail = function(user) {
		//console.log(user.email);
		return $http({
			method : "POST",
			url : 'forgotpassword',
			data : user
		})
	}
	
	return reset;
});