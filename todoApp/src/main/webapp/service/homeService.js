var todoApp = angular.module('todoApp');

todoApp.factory('homeService', function($http, $location) {
	var homePage = {};
	
	homePage.homeuser = function(user) {
		console.log(user.email);
		return $http({
			method : "GET",
			url : 'home',
			data : user
		});
	}
	return homePage;
});